package tech.xirius.payment.infrastructure.adapter.payu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.domain.model.PaymentMethod;
import tech.xirius.payment.infrastructure.adapter.payu.dto.RechargeRequest;
import tech.xirius.payment.infrastructure.adapter.payu.dto.GatewayTransaction;
import tech.xirius.payment.infrastructure.config.PayuConfig;

/**
 * Adaptador de la pasarela de pagos PayU para procesar pagos y consultar el
 * estado de las transacciones.
 * Este componente se encarga de interactuar con la API de PayU para realizar
 * transacciones de pago, generando las solicitudes y procesando las respuestas
 * de forma adecuada.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PayUPaymentGatewayAdapter implements PaymentGatewayPort {

    private final PayuConfig payuConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    /**
     * Procesa un pago utilizando la pasarela PayU.
     * 
     * @param rechargeRequest Detalles del pago a procesar.
     * @return Un mapa con la respuesta de la API de PayU
     */
    @Override
    public Map<String, Object> processPayment(RechargeRequest rechargeRequest) {
        log.info("Procesando pago con PayU para usuario: {}", rechargeRequest.userId());
        // Crear referencia única para esta transacción
        String referenceCode = generateReferenceCode(rechargeRequest);
        // Calcular firma para la transacción
        String signature = createSignature(rechargeRequest, referenceCode);
        // Crear el cuerpo de la solicitud como un objeto JSON
        ObjectNode requestNode = createRequestBody(rechargeRequest, signature, referenceCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ObjectNode> entity = new HttpEntity<>(requestNode, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    payuConfig.getApi().getUrl() + "/payments-api/4.0/service.cgi",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

            return response.getBody();
        } catch (Exception e) {
            log.error("Error procesando pago PSE con PayU", e);
            throw new RuntimeException("Error al procesar el pago con PayU", e);
        }
    }

    /**
     * Genera un código de referencia único para la transacción.
     * 
     * @param request Detalles de la solicitud de recarga.
     * @return Un código de referencia único.
     */
    private String generateReferenceCode(RechargeRequest request) {
        return "REF-" + System.currentTimeMillis() + "-"
                + request.userId().substring(0, Math.min(8, request.userId().length()));
    }

    /**
     * Crea el cuerpo de la solicitud de pago como un objeto JSON.
     * 
     * @param rechargeRequest Detalles de la solicitud de recarga.
     * @param signature       La firma de la transacción.
     * @param referenceCode   El código de referencia único de la transacción.
     * @return El cuerpo de la solicitud como un objeto JSON.
     */
    private ObjectNode createRequestBody(RechargeRequest rechargeRequest, String signature,
            String referenceCode) {
        try {
            // Valores del IVA y base gravable
            BigDecimal taxRate = new BigDecimal("0.19"); // 19% de IVA en Colombia
            BigDecimal taxValue = rechargeRequest.amount().multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal taxReturnBase = rechargeRequest.amount().subtract(taxValue).setScale(2, RoundingMode.HALF_UP);
            String notifyUrl = "";
            // Crear el objeto JSON raíz
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("language", payuConfig.getTransaction().getLanguage());
            rootNode.put("command", "SUBMIT_TRANSACTION");

            // Información del comercio
            ObjectNode merchantNode = rootNode.putObject("merchant");
            merchantNode.put("apiKey", payuConfig.getApi().getKey());
            merchantNode.put("apiLogin", payuConfig.getApi().getLogin());

            // Información de la transacción
            ObjectNode transactionNode = rootNode.putObject("transaction");

            // Información de la orden
            ObjectNode orderNode = transactionNode.putObject("order");
            orderNode.put("accountId", payuConfig.getAccount().getId());
            orderNode.put("referenceCode", referenceCode);
            orderNode.put("description", rechargeRequest.description());
            orderNode.put("language", payuConfig.getTransaction().getLanguage());
            orderNode.put("signature", signature);
            orderNode.put("notifyUrl", notifyUrl);

            // Valores adicionales de la transacción
            ObjectNode additionalValuesNode = orderNode.putObject("additionalValues");

            // Valor de la transacción
            ObjectNode txValueNode = additionalValuesNode.putObject("TX_VALUE");
            txValueNode.put("value", rechargeRequest.amount());
            txValueNode.put("currency", "COP");

            // Valor del IVA
            ObjectNode txTaxNode = additionalValuesNode.putObject("TX_TAX");
            txTaxNode.put("value", taxValue);
            txTaxNode.put("currency", "COP");

            // Base gravable
            ObjectNode txTaxBaseNode = additionalValuesNode.putObject("TX_TAX_RETURN_BASE");
            txTaxBaseNode.put("value", taxReturnBase);
            txTaxBaseNode.put("currency", "COP");

            // Información del comprador
            ObjectNode buyerNode = orderNode.putObject("buyer");
            buyerNode.put("merchantBuyerId", rechargeRequest.userId());
            buyerNode.put("fullName", rechargeRequest.fullName());
            buyerNode.put("emailAddress", rechargeRequest.emailAddress());
            buyerNode.put("contactPhone", rechargeRequest.contactPhone());
            buyerNode.put("dniNumber", rechargeRequest.dniNumber());

            ObjectNode buyerShippingNode = buyerNode.putObject("shippingAddress");
            buyerShippingNode.put("street1", rechargeRequest.shippingAddress().street1());
            buyerShippingNode.put("street2", rechargeRequest.shippingAddress().street2());
            buyerShippingNode.put("city", rechargeRequest.shippingAddress().city());
            buyerShippingNode.put("state", rechargeRequest.shippingAddress().state());
            buyerShippingNode.put("country", rechargeRequest.shippingAddress().country());
            buyerShippingNode.put("postalCode", rechargeRequest.shippingAddress().postalCode());
            buyerShippingNode.put("phone", rechargeRequest.shippingAddress().phone());

            // Dirección de envío en la orden
            ObjectNode shippingAddressNode = orderNode.putObject("shippingAddress");
            shippingAddressNode.put("street1", rechargeRequest.shippingAddress().street1());
            shippingAddressNode.put("street2", rechargeRequest.shippingAddress().street2());
            shippingAddressNode.put("city", rechargeRequest.shippingAddress().city());
            shippingAddressNode.put("state", rechargeRequest.shippingAddress().state());
            shippingAddressNode.put("country", rechargeRequest.shippingAddress().country());
            shippingAddressNode.put("postalCode", rechargeRequest.shippingAddress().postalCode());
            shippingAddressNode.put("phone", rechargeRequest.shippingAddress().phone());

            // Información del pagador
            ObjectNode payerNode = transactionNode.putObject("payer");
            payerNode.put("merchantPayerId", rechargeRequest.userId());
            payerNode.put("fullName", rechargeRequest.fullName());
            payerNode.put("emailAddress", rechargeRequest.emailAddress());
            payerNode.put("contactPhone", rechargeRequest.contactPhone());
            payerNode.put("dniNumber", rechargeRequest.dniNumber());
            payerNode.put("dniType", rechargeRequest.dniType());

            // Dirección de envío del pagador
            ObjectNode billingAddressNode = payerNode.putObject("billingAddress");
            billingAddressNode.put("street1", rechargeRequest.shippingAddress().street1());
            billingAddressNode.put("street2", rechargeRequest.shippingAddress().street2());
            billingAddressNode.put("city", rechargeRequest.shippingAddress().city());
            billingAddressNode.put("state", rechargeRequest.shippingAddress().state());
            billingAddressNode.put("country", rechargeRequest.shippingAddress().country());
            billingAddressNode.put("postalCode", rechargeRequest.shippingAddress().postalCode());
            billingAddressNode.put("phone", rechargeRequest.shippingAddress().phone());

            if (rechargeRequest.paymentMethod() == PaymentMethod.CREDIT
                    || rechargeRequest.paymentMethod() == PaymentMethod.DEBIT) {
                // Información de la tarjeta de crédito
                ObjectNode creditCardNode = transactionNode.putObject("creditCard");
                creditCardNode.put("number", rechargeRequest.cardDetails().creditCardNumber());
                creditCardNode.put("securityCode", rechargeRequest.cardDetails().securityCode());
                creditCardNode.put("expirationDate", rechargeRequest.cardDetails().expirationDate());
                creditCardNode.put("name", rechargeRequest.cardDetails().cardHolderName());

                // Parámetros extra
                ObjectNode extraParamsNode = transactionNode.putObject("extraParameters");
                extraParamsNode.put("INSTALLMENTS_NUMBER", 1); // Número de cuotas (1 para pago total)
            } else if (rechargeRequest.paymentMethod() == PaymentMethod.PSE) {
                ObjectNode extraParamsNode = transactionNode.putObject("extraParameters");
                extraParamsNode.put("RESPONSE_URL", "http://www.payu.com/response");
                extraParamsNode.put("PSE_REFERENCE1", "127.0.0.1");
                extraParamsNode.put("FINANCIAL_INSTITUTION_CODE", "1022"); // Código de la entidad bancaria
                extraParamsNode.put("USER_TYPE", rechargeRequest.userType());
                extraParamsNode.put("PSE_REFERENCE2", rechargeRequest.dniType());
                extraParamsNode.put("PSE_REFERENCE3", "123456789"); // Referencia adicional de PSE
            } else {
                throw new IllegalArgumentException("Método de pago no soportado: " + rechargeRequest.paymentMethod());
            }

            // Tipo de transacción y método de pago
            transactionNode.put("type", "AUTHORIZATION_AND_CAPTURE");

            if (rechargeRequest.paymentMethod() == PaymentMethod.PSE) {
                transactionNode.put("paymentMethod", "PSE");
            } else if (rechargeRequest.paymentMethod() == PaymentMethod.CREDIT
                    || rechargeRequest.paymentMethod() == PaymentMethod.DEBIT) {
                transactionNode.put("paymentMethod", "VISA"); // Debe capturar el tipo de tarjeta (NOSE COMO)
            }

            transactionNode.put("paymentCountry", payuConfig.getTransaction().getPaymentCountry());

            // Información del dispositivo y navegador (Por Capturar)
            transactionNode.put("deviceSessionId", "vghs6tvkcle931686k1900o6e1");
            transactionNode.put("ipAddress", "127.0.0.1");
            transactionNode.put("cookie", "pt1t38347bs6jc9ruv2ecpv7o2");
            transactionNode.put("userAgent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            // Modo de prueba
            rootNode.put("test", payuConfig.getTransaction().isTest());

            return rootNode;
        } catch (Exception e) {
            log.error("Error al crear el cuerpo de la solicitud: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear el cuerpo de la solicitud: " + e.getMessage(), e);
        }
    }

    /**
     * Crea la firma digital para autenticar la transacción con PayU.
     * 
     * @param rechargeRequest Detalles de la solicitud de recarga.
     * @param referenceCode   El código de referencia único de la transacción.
     * @return La firma digital de la transacción.
     */
    private String createSignature(RechargeRequest rechargeRequest, String referenceCode) {
        try {
            String raw = payuConfig.getApi().getKey() + "~" + payuConfig.getMerchant().getId() + "~" + referenceCode
                    + "~" + rechargeRequest.amount() + "~"
                    + "COP";
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("Error generando la firma de PayU", e);
            throw new RuntimeException("Error generando la firma de PayU", e);
        }
    }

    /**
     * Consulta el estado de una transacción con PayU.
     * 
     * @param transactionId El identificador de la transacción.
     * @return Un mapa con los detalles de la transacción
     */
    @Override
    public ObjectNode getTransactionStatus(GatewayTransaction transactionId) {
        log.info("Consultando Transaccion para : {}", transactionId);
        // Crear referencia única para esta transacción

        // Crear el cuerpo de la solicitud como un objeto JSON
        ObjectNode requestNode = createRequestBody(transactionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ObjectNode> entity = new HttpEntity<>(requestNode, headers);

        try {
            ResponseEntity<ObjectNode> response = restTemplate.exchange(
                    payuConfig.getApi().getUrl() + "/reports-api/4.0/service.cgi",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

            return response.getBody();
        } catch (Exception e) {
            log.error("Error procesando consulta", e);
            throw new RuntimeException("Error al procesar la consulta con PayU", e);
        }
    }

    /**
     * Crea el cuerpo de la solicitud para consultar el estado de una transacción.
     * 
     * @param transactionId El identificador de la transacción.
     * @return El cuerpo de la solicitud como un objeto JSON.
     */
    private ObjectNode createRequestBody(GatewayTransaction transactionId) {
        try {
            // Crear el objeto JSON raíz
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("test", payuConfig.getTransaction().isTest());
            rootNode.put("language", payuConfig.getTransaction().getLanguage());
            rootNode.put("command", "TRANSACTION_RESPONSE_DETAIL");

            // Información del comercio
            ObjectNode merchantNode = rootNode.putObject("merchant");
            merchantNode.put("apiLogin", payuConfig.getApi().getLogin());
            merchantNode.put("apiKey", payuConfig.getApi().getKey());

            ObjectNode detailsNode = rootNode.putObject("details");
            detailsNode.put("transactionId", transactionId.gatewayTransactionId());

            return rootNode;
        } catch (Exception e) {
            log.error("Error al crear el cuerpo de la solicitud: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear el cuerpo de la solicitud: " + e.getMessage(), e);
        }
    }

}

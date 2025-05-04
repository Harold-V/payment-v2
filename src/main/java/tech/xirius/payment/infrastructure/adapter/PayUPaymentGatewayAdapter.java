package tech.xirius.payment.infrastructure.adapter;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.infrastructure.config.PayuConfig;
import tech.xirius.payment.infrastructure.web.dto.RechargeRequest;
import tech.xirius.payment.infrastructure.web.dto.PaymentResponse;

/**
 * Adaptador para el gateway de pago PayU.
 * Implementa la interfaz PaymentGatewayPort para procesar pagos a través de
 * PayU.
 */
@Component
@RequiredArgsConstructor
public class PayUPaymentGatewayAdapter implements PaymentGatewayPort {

    private final WebClient.Builder webClientBuilder;
    private final PayuConfig payuConfig;
    private final ObjectMapper objectMapper;

    @Override
    public PaymentResponse processPayment(RechargeRequest rechargeRequest) {
        // Construir el cuerpo de la solicitud según la configuración de PayU
        String signature = createSignature(rechargeRequest); // Puedes implementar este método según los parámetros de
                                                             // PayU

        // Crear la solicitud
        String requestBody = createRequestBody(rechargeRequest, signature);

        // Crear el WebClient para realizar la solicitud HTTP
        WebClient webClient = webClientBuilder.baseUrl(payuConfig.getApi().getUrl()).build();

        // Realizar la solicitud HTTP a la API de PayU
        Mono<String> response = webClient
                .post() // Usamos post() para enviar los datos
                .bodyValue(requestBody) // Pasamos el cuerpo de la solicitud
                .retrieve() // Recuperamos la respuesta
                .bodyToMono(String.class); // Especificamos que la respuesta será un String (JSON)

        // Mapear la respuesta JSON a un objeto PaymentResponse
        String responseBody = response.block(); // Obtener la respuesta como un String (JSON)

        // Usamos el método mapToPaymentResponse para convertir el JSON en un objeto
        // PaymentResponse
        return mapToPaymentResponse(responseBody); // Convertimos el JSON de PayU a PaymentResponse
    }

    private String createRequestBody(RechargeRequest rechargeRequest, String signature) {

        return "{\n" +
                "   \"language\": \"" + payuConfig.getTransaction().getLanguage() + "\",\n" +
                "   \"command\": \"" + payuConfig.getTransaction().getCommand() + "\",\n" +
                "   \"merchant\": {\n" +
                "      \"apiKey\": \"" + payuConfig.getApi().getKey() + "\",\n" +
                "      \"apiLogin\": \"" + payuConfig.getApi().getLogin() + "\"\n" +
                "   },\n" +
                "   \"transaction\": {\n" +
                "      \"order\": {\n" +
                "         \"accountId\": \"" + payuConfig.getAccount().getId() + "\",\n" +
                "         \"referenceCode\": \"" + "REF-" + System.currentTimeMillis() + "\",\n" +
                "         \"description\": \"" + rechargeRequest.description() + "\",\n" +
                "         \"language\": \"" + payuConfig.getTransaction().getLanguage() + "\",\n" +
                "         \"signature\": \"" + signature + "\",\n" +
                "         \"notifyUrl\": \"" + rechargeRequest.notifyUrl() + "\",\n" +
                "         \"additionalValues\": {\n" +
                "            \"TX_VALUE\": {\n" +
                "               \"value\": " + rechargeRequest.amount() + ",\n" +
                "               \"currency\": \"COP\"\n" +
                "            },\n" +
                "            \"TX_TAX\": {\n" +
                "               \"value\": " + rechargeRequest.amount().multiply(BigDecimal.valueOf(0.19)) + ",\n" +
                "               \"currency\": \"COP\"\n" +
                "            },\n" +
                "            \"TX_TAX_RETURN_BASE\": {\n" +
                "               \"value\": "
                + rechargeRequest.amount().subtract(rechargeRequest.amount().multiply(BigDecimal.valueOf(0.19))) + ",\n"
                +
                "               \"currency\": \"COP\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"buyer\": {\n" +
                "            \"merchantBuyerId\": \"" + rechargeRequest.userId() + "\",\n" +
                "            \"fullName\": \"" + rechargeRequest.fullName() + "\",\n" +
                "            \"emailAddress\": \"" + rechargeRequest.emailAddress() + "\",\n" +
                "            \"contactPhone\": \"" + rechargeRequest.contactPhone() + "\",\n" +
                "            \"dniNumber\": \"" + rechargeRequest.dniNumber() + "\",\n" +
                "            \"shippingAddress\": {\n" +
                "               \"street1\": \"" + rechargeRequest.shippingAddress().street1() + "\",\n" +
                "               \"street2\": \"" + rechargeRequest.shippingAddress().street2() + "\",\n" +
                "               \"city\": \"" + rechargeRequest.shippingAddress().city() + "\",\n" +
                "               \"state\": \"" + rechargeRequest.shippingAddress().state() + "\",\n" +
                "               \"country\": \"" + rechargeRequest.shippingAddress().country() + "\",\n" +
                "               \"postalCode\": \"" + rechargeRequest.shippingAddress().postalCode() + "\",\n" +
                "               \"phone\": \"" + rechargeRequest.shippingAddress().phone() + "\"\n" +
                "            }\n" +
                "         },\n" +
                "         \"shippingAddress\": {\n" +
                "            \"street1\": \"" + rechargeRequest.shippingAddress().street1() + "\",\n" +
                "            \"street2\": \"" + rechargeRequest.shippingAddress().street2() + "\",\n" +
                "            \"city\": \"" + rechargeRequest.shippingAddress().city() + "\",\n" +
                "            \"state\": \"" + rechargeRequest.shippingAddress().state() + "\",\n" +
                "            \"country\": \"" + rechargeRequest.shippingAddress().country() + "\",\n" +
                "            \"postalCode\": \"" + rechargeRequest.shippingAddress().postalCode() + "\",\n" +
                "            \"phone\": \"" + rechargeRequest.shippingAddress().phone() + "\"\n" +
                "         }\n" +
                "      },\n" +
                "   },\n" +
                "   \"payer\": {\n" +
                "      \"merchantPayerId\": \"" + rechargeRequest.userId() + "\",\n" +
                "      \"fullName\": \"" + rechargeRequest.fullName() + "\",\n" +
                "      \"emailAddress\": \"" + rechargeRequest.emailAddress() + "\"\n" +
                "   },\n" +
                "   \"creditCard\": {\n" +
                "      \"number\": \"" + rechargeRequest.creditCardNumber() + "\",\n" +
                "      \"securityCode\": \"" + rechargeRequest.securityCode() + "\",\n" +
                "      \"expirationDate\": \"" + rechargeRequest.expirationDate() + "\",\n" +
                "      \"name\": \"" + rechargeRequest.cardHolderName() + "\"\n" +
                "   },\n" +
                "   \"test\": " + payuConfig.getTransaction().isTest() + "\n" +
                "}";
    }

    private PaymentResponse mapToPaymentResponse(String responseBody) {
        try {
            // Convertir el JSON en un JsonNode para obtener el valor de los campos
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // Acceder a los campos específicos del JSON de PayU
            String orderId = rootNode.path("transaction").path("orderId").asText();
            String transactionId = rootNode.path("transaction").path("transactionId").asText();
            String status = rootNode.path("transaction").path("state").asText();

            // Mapear el status de PayU a un TransactionStatus
            TransactionStatus transactionStatus = TransactionStatus.valueOf(status.toUpperCase());

            // Devolver el PaymentResponse
            return new PaymentResponse(orderId, transactionId, transactionStatus);

        } catch (Exception e) {
            // Manejo de errores en caso de que el JSON sea inválido o no se pueda mapear
            e.printStackTrace();
            throw new RuntimeException("Error al mapear la respuesta de PayU: " + e.getMessage());
        }
    }

    private String createSignature(RechargeRequest paymentRequest) {
        // Implementar el algoritmo de firma de PayU, usando su API y sus reglas
        return "signature"; // Firma de ejemplo, reemplazar con el cálculo real
    }

}

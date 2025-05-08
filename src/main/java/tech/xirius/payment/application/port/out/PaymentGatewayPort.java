package tech.xirius.payment.application.port.out;

import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

import tech.xirius.payment.infrastructure.adapter.payu.dto.RechargeRequest;
import tech.xirius.payment.infrastructure.adapter.payu.dto.GatewayTransaction;

/**
 * Interfaz para la comunicación con el gateway de pagos.
 */
public interface PaymentGatewayPort {

    Map<String, Object> processPayment(RechargeRequest request);

    ObjectNode getTransactionStatus(GatewayTransaction transactionId);
}

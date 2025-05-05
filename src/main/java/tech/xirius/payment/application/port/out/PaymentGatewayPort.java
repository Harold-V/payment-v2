package tech.xirius.payment.application.port.out;

import java.util.Map;

import tech.xirius.payment.infrastructure.adapter.payu.dto.RechargeRequest;
import tech.xirius.payment.infrastructure.adapter.payu.dto.Transaction;

/**
 * Interfaz para la comunicación con el gateway de pagos.
 */
public interface PaymentGatewayPort {

    Map<String, Object> processPayment(RechargeRequest request);

    Map<String, Object> getTransactionStatus(Transaction transactionId);
}

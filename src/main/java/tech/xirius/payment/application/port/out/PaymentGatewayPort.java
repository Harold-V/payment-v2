package tech.xirius.payment.application.port.out;

import tech.xirius.payment.infrastructure.web.dto.RechargeRequest;

import java.util.Map;

/**
 * Interfaz para la comunicación con el gateway de pagos.
 */
public interface PaymentGatewayPort {

    Map<String, Object> processPayment(RechargeRequest request);
}

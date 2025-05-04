package tech.xirius.payment.application.port.out;

import tech.xirius.payment.infrastructure.web.dto.PaymentRequest;
import tech.xirius.payment.infrastructure.web.dto.PaymentResponse;

/**
 * Interfaz para la comunicación con el gateway de pagos.
 */
public interface PaymentGatewayPort {

    PaymentResponse processPayment(PaymentRequest request);
}

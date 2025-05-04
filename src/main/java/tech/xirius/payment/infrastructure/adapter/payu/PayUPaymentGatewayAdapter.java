package tech.xirius.payment.infrastructure.adapter.payu;

import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.infrastructure.web.dto.PaymentRequest;
import tech.xirius.payment.infrastructure.web.dto.PaymentResponse;

/**
 * Adaptador para el gateway de pago PayU.
 * Implementa la interfaz PaymentGatewayPort para procesar pagos a través de
 * PayU.
 */
public class PayUPaymentGatewayAdapter implements PaymentGatewayPort {

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Implement PayU payment processing logic here
        return new PaymentResponse();
    }

}

package tech.xirius.payment.infrastructure.adapter;

import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.infrastructure.web.dto.PaymentRequest;
import tech.xirius.payment.infrastructure.web.dto.PaymentResponse;

public class PayUPaymentGatewayAdapter implements PaymentGatewayPort {

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Implement PayU payment processing logic here
        return new PaymentResponse();
    }

}

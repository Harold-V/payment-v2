package tech.xirius.payment.application.ports.out;

import tech.xirius.payment.infrastructure.web.dto.PaymentRequest;
import tech.xirius.payment.infrastructure.web.dto.PaymentResponse;

public interface PaymentGatewayPort {

    PaymentResponse processPayment(PaymentRequest request);
}

package tech.xirius.payment.application.port.in;

import tech.xirius.payment.domain.model.Payment;
import tech.xirius.payment.infrastructure.adapter.payu.dto.RechargeRequest;

public interface CreatePaymentUseCase {
    Payment create(RechargeRequest paymentRequest);
}

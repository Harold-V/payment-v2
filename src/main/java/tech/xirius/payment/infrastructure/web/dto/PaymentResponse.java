package tech.xirius.payment.infrastructure.web.dto;

import tech.xirius.payment.domain.model.TransactionStatus;

public record PaymentResponse(
        String orderId,
        String transactionId,
        TransactionStatus state) {

}

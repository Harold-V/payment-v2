package tech.xirius.payment.application.port.in;

import java.util.Map;

import tech.xirius.payment.infrastructure.adapter.payu.dto.Transaction;

public interface GetTransactionStatusUseCase {
    Map<String, Object> getTransactionStatus(Transaction transactionId);
}

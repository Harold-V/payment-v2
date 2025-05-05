package tech.xirius.payment.application.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.GetTransactionStatusUseCase;
import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.infrastructure.adapter.payu.dto.Transaction;

@Service
@RequiredArgsConstructor
public class GetTransactionStatusService implements GetTransactionStatusUseCase {

    private final PaymentGatewayPort paymentGatewayPort;

    @Override
    public Map<String, Object> getTransactionStatus(Transaction transactionId) {
        return paymentGatewayPort.getTransactionStatus(transactionId);
    }

}

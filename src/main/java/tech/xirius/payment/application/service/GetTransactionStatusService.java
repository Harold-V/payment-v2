package tech.xirius.payment.application.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.GetTransactionStatusUseCase;
import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.infrastructure.adapter.payu.dto.GatewayTransaction;

@Service
@RequiredArgsConstructor
public class GetTransactionStatusService implements GetTransactionStatusUseCase {

    private final PaymentGatewayPort paymentGatewayPort;

    @Override
    public ObjectNode getTransactionStatus(GatewayTransaction transactionId) {
        return paymentGatewayPort.getTransactionStatus(transactionId);
    }

}

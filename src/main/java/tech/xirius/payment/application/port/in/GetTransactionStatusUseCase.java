package tech.xirius.payment.application.port.in;

import com.fasterxml.jackson.databind.node.ObjectNode;

import tech.xirius.payment.infrastructure.adapter.payu.dto.GatewayTransaction;

/**
 * Caso de uso para obtener el estado de una transacción a traves del api de
 * payu.
 */
public interface GetTransactionStatusUseCase {
    ObjectNode getTransactionStatus(GatewayTransaction transactionId);
}

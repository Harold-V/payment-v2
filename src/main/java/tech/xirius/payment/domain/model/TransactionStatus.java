package tech.xirius.payment.domain.model;

/**
 * Representa el estado de la transacción realizada en la billetera del usuario.
 */
public enum TransactionStatus {
    APPROVED,
    FAILED,
    REJECTED,
    PENDING
}

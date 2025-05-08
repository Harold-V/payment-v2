package tech.xirius.payment.domain.model;

/**
 * Representa el estado de la transacción realizada en la billetera del usuario.
 */
public enum TransactionStatus {
    /**
     * Representa el estado de la transacción aprobada.
     */
    APPROVED,
    /**
     * Representa el estado de la transacción fallida.
     */
    FAILED,
    /**
     * Representa el estado de la transacción rechazada.
     */
    REJECTED,
    /**
     * Representa el estado de la transacción pendiente.
     */
    PENDING
}

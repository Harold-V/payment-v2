package tech.xirius.payment.domain.model;

/**
 * Representa el tipo de transacción realizada en la billetera del usuario.
 */
public enum WalletTransactionType {
    /**
     * Representa una transacción de recarga de saldo en la billetera del usuario.
     */
    RECHARGE,
    /**
     * Representa una transacción de compra realizada con el saldo de la
     * billetera del usuario.
     */
    DEDUCT
}

package tech.xirius.payment.domain.model;

/**
 * Representa los métodos de pago disponibles para el usuario.
 */
public enum PaymentMethod {
    /**
     * Representa un método de pago con tarjeta de crédito.
     */
    CREDIT,
    /**
     * Representa un método de pago con tarjeta de débito.
     */
    DEBIT,
    /**
     * Representa un método de pago via PSE (Pagos Seguros en Línea).
     */
    PSE
}

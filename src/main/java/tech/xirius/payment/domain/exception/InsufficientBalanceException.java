package tech.xirius.payment.domain.exception;

/**
 * Excepción lanzada cuando el saldo de la billetera es insuficiente para
 * realizar una transacción.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

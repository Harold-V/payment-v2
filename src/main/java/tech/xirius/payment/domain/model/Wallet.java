package tech.xirius.payment.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.xirius.payment.domain.exception.InsufficientBalanceException;

/*
 * Representa una billetera digital para un usuario.
 * Permite realizar recargas y deducciones de saldo.
 */
@AllArgsConstructor
@Getter
public class Wallet {

    /**
     * ID de la billetera.
     * <p>
     * Este ID es único y se genera automáticamente al crear la billetera.
     * </p>
     */
    private final UUID walletId;
    /**
     * ID del usuario propietario de la billetera.
     * <p>
     * Este ID se utiliza para identificar al usuario asociado a la billetera.
     * </p>
     */
    private final String userId;
    /**
     * Saldo actual de la billetera.
     * <p>
     * Este campo representa la cantidad de dinero disponible en la billetera.
     * </p>
     */
    private Money balance;

    /**
     * Recarga la billetera con una cantidad específica de dinero.
     * 
     * @param amount
     */
    public void recharge(Money amount) {
        validateCurrency(amount);
        this.balance = this.balance.add(amount);
    }

    /**
     * Deduce una cantidad específica de dinero de la billetera.
     * 
     * @param amount
     */
    public void deduct(Money amount) {
        if (!this.balance.isGreaterThanOrEqual(amount)) {
            throw new InsufficientBalanceException(
                    "Saldo insuficiente.");
        }
        validateCurrency(amount);
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Valida que la moneda de la cantidad a recargar o deducir sea la misma que la
     * de la billetera.
     * 
     * @param amount
     */
    private void validateCurrency(Money amount) {
        if (!this.balance.getCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }
}

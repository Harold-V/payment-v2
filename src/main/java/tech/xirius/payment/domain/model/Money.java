package tech.xirius.payment.domain.model;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Representa una cantidad de dinero junto con su moneda.
 * <p>
 * Esta clase encapsula un valor monetario y su moneda asociada. Proporciona
 * métodos para realizar operaciones aritméticas y comparaciones entre
 * cantidades de dinero.
 * </p>
 */
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Money {

    /**
     * El valor monetario representado como un BigDecimal.
     * <p>
     * >
     * Este valor puede ser positivo o negativo, dependiendo de la operación
     * realizada.
     * </p>
     */
    private final BigDecimal amount;
    /**
     * La moneda asociada a este valor monetario.
     */
    private final Currency currency;

    /**
     * Método de fábrica para crear una nueva instancia de Money.
     * 
     * @param amount   El valor monetario
     * @param currency La moneda asociada al valor
     * @return Una nueva instancia de Money con el monto y moneda especificados
     */
    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    /**
     * Suma otra cantidad de dinero a esta y devuelve el resultado como una nueva
     * instancia.
     * <p>
     * Ambas cantidades deben tener la misma moneda para poder realizar la
     * operación.
     * </p>
     *
     * @param other La cantidad de dinero a sumar
     * @return Una nueva instancia de Money que representa la suma
     */
    public Money add(Money other) {
        validateCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    /**
     * Resta otra cantidad de dinero de esta y devuelve el resultado como una nueva
     * instancia.
     * <p>
     * Ambas cantidades deben tener la misma moneda para poder realizar la
     * operación.
     * </p>
     *
     * @param other La cantidad de dinero a restar
     * @return Una nueva instancia de Money que representa la diferencia
     */
    public Money subtract(Money other) {
        validateCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    /**
     * Compara si esta cantidad es mayor o igual que otra cantidad.
     * <p>
     * Ambas cantidades deben tener la misma moneda para poder realizar la
     * comparación.
     * </p>
     *
     * @param other La cantidad de dinero a comparar
     * @return true si esta cantidad es mayor o igual que la otra, false en caso
     *         contrario
     */
    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount.compareTo(other.amount) >= 0;
    }

    /**
     * Valida que la moneda de otra cantidad de dinero sea igual a la de esta.
     *
     * @param other La cantidad de dinero cuya moneda será validada
     */

    private void validateCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot operate money with different currencies");
        }
    }
}

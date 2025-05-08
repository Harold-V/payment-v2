package tech.xirius.payment.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import tech.xirius.payment.domain.model.Currency;

/**
 * Representa una cantidad de dinero con su respectiva moneda.
 * Esta clase es embebida en otras entidades que necesiten almacenar valores
 * monetarios.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MoneyEmbedded {

    /**
     * Cantidad de dinero.
     */
    private BigDecimal amount;

    /**
     * Moneda asociada a la cantidad de dinero.
     */
    private Currency currency;

}

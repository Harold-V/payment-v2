package tech.xirius.payment.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.xirius.payment.domain.model.Currency;

/**
 * Entidad que representa una billetera de usuario en la base de datos.
 * Esta clase mapea la tabla wallet en la base de datos y almacena los
 * detalles relacionados con la billetera,
 * como el ID de usuario, el saldo y la moneda asociada.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "wallet", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class WalletEntity {

    /**
     * ID único de la billetera del usuario.
     * Este campo es la clave primaria en la tabla de billeteras.
     */
    @Id
    @Column(name = "wallet_id", nullable = false, updatable = false)
    private UUID walletId;

    /**
     * ID del usuario al que pertenece la billetera.
     * Este campo debe ser único para cada usuario.
     */
    @Column(name = "user_id", nullable = false, unique = true, length = 255)
    private String userId;

    /**
     * Saldo actual de la billetera.
     * Este campo almacena el valor del saldo disponible en la billetera del
     * usuario.
     */
    @Column(nullable = false, precision = 38, scale = 2)
    @Embedded
    private MoneyEmbedded balance;

    /**
     * Moneda asociada con la billetera.
     * Este campo indica la moneda en la que está configurada la billetera (por
     * ejemplo, COP, EUR).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;
}

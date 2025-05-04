package tech.xirius.payment.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "wallet", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class WalletEntity {

    @Id
    @Column(name = "wallet_id", nullable = false, updatable = false)
    private UUID id; // ID de la billetera del usuario

    @Column(name = "user_id", nullable = false, unique = true, length = 255)
    private String userId; // ID del usuario al que pertenece la billetera

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal balance; // Saldo actual de la billetera

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency; // Moneda de la billetera (COP, EUR, etc.)
}

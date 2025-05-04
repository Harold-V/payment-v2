package tech.xirius.payment.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet_transactions")
public class WalletTransactionEntity {

    @Id
    @Column(name = "wallet_transaction_id", nullable = false, updatable = false, unique = true)
    private UUID id; // ID de la transacción de la billetera

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet; // ID de la billetera asociada a la transacción

    @Column(name = "amount", nullable = false, precision = 38, scale = 2)
    private BigDecimal amount; // Monto de la transacción

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency; // Moneda de la transacción (COP, EUR, etc.)

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WalletTransactionType type; // Tipo de transacción (recarga o deducción)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status; // Estado de la transacción (aprobada, fallida, rechazada, pendiente)

    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp; // Fecha y hora de la transacción

    @Column(name = "previous_balance", nullable = false, precision = 38, scale = 2)
    private BigDecimal previousBalance; // Saldo anterior de la billetera antes de la transacción

    @Column(name = "new_balance", nullable = false, precision = 38, scale = 2)
    private BigDecimal newBalance; // Saldo nuevo de la billetera después de la transacción

}

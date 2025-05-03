package tech.xirius.payment.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.WalletTransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet_transactions")
public class WalletTransactionEntity {

    @Id
    @Column(name = "transaction_id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet;

    @Column(name = "amount", nullable = false, precision = 38, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WalletTransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WalletTransactionStatus status;

    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "previous_balance", nullable = false, precision = 38, scale = 2)
    private BigDecimal previousBalance;

    @Column(name = "new_balance", nullable = false, precision = 38, scale = 2)
    private BigDecimal newBalance;

}

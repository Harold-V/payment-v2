package tech.xirius.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Representa una transacción realizada en la billetera del usuario.
 */
@Getter
@AllArgsConstructor
public class WalletTransaction {

    private final UUID id;

    private final UUID walletId;

    private final Money amount;

    private final WalletTransactionType type;

    private final WalletTransactionStatus status;

    private final ZonedDateTime timestamp;

    private final UUID paymentId;

    private final BigDecimal previousBalance;

    private final BigDecimal newBalance;

    private final String message;

    public static WalletTransaction create(UUID walletId, Money amount, WalletTransactionType type,
            WalletTransactionStatus status, UUID paymentId,
            BigDecimal previousBalance, BigDecimal newBalance,
            String message) {
        return new WalletTransaction(
                UUID.randomUUID(),
                walletId,
                amount,
                type,
                status,
                ZonedDateTime.now(),
                paymentId,
                previousBalance,
                newBalance,
                message);
    }

}

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

}

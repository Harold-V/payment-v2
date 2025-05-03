package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.WalletTransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;

public record WalletTransactionResponse(UUID id,
        String userId,
        BigDecimal amount,
        Currency currency,
        WalletTransactionType type,
        WalletTransactionStatus status,
        ZonedDateTime timestamp,
        UUID paymentId,
        BigDecimal previousBalance,
        BigDecimal newBalance) {

}

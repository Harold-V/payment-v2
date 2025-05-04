package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;

@Schema(description = "Representa una transacción realizada en la billetera del usuario.")
public record WalletTransactionResponse(
        @Schema(description = "ID de la transacción") UUID id,
        @Schema(description = "ID de el usuario") String userId,
        @Schema(description = "ID de la billetera asociada a la transacción") UUID walletId,
        @Schema(description = "Monto de la transacción") BigDecimal amount,
        @Schema(description = "Moneda de la transacción") Currency currency,
        @Schema(description = "Tipo de transacción (recarga o deducción)") WalletTransactionType type,
        @Schema(description = "Estado de la transacción (aprobada, fallida, rechazada, pendiente)") TransactionStatus status,
        @Schema(description = "Fecha y hora de la transacción") ZonedDateTime timestamp,
        @Schema(description = "Saldo anterior de la billetera antes de la transacción") BigDecimal previousBalance,
        @Schema(description = "Saldo nuevo de la billetera después de la transacción") BigDecimal newBalance) {

}

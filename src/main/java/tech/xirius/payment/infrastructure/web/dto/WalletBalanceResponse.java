package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.xirius.payment.domain.model.Currency;

public record WalletBalanceResponse(
        @Schema(description = "ID de la billetera asociada al usuario") String userId,
        @Schema(description = "Saldo de la billetera") BigDecimal balance,
        @Schema(description = "Moneda de la billetera") Currency currency) {

}

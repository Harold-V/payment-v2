package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RechargeRequest(
        @Schema(description = "ID de el usuario") @NotNull(message = "El userId no puede ser nulo") String userId,

        @Schema(description = "Monto a recargar") @NotNull(message = "El amount no puede ser nulo") @Positive(message = "El amount debe ser un número positivo") BigDecimal amount) {
}

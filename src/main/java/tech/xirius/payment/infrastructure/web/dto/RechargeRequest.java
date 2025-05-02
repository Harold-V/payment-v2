package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RechargeRequest(
        @NotNull(message = "El userId no puede ser nulo") @JsonProperty("userId") String userId,

        @NotNull(message = "El amount no puede ser nulo") @Positive(message = "El amount debe ser un número positivo") @JsonProperty("amount") BigDecimal amount) {
}

package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;

public record DeductRequest(
                String userId,
                BigDecimal amount) {
}

package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;

import tech.xirius.payment.domain.model.Currency;

public record WalletBalanceResponse(
                String userId,
                BigDecimal balance, Currency currency) {

}

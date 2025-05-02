package tech.xirius.payment.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Wallet {
    private final UUID id;
    private final String userId;
    private Money balance;

    public void recharge(Money amount) {
        validateCurrency(amount);
        this.balance = this.balance.add(amount);
    }

    public void deduct(Money amount) {
        if (!this.balance.isGreaterThanOrEqual(amount)) {
            throw new InsufficientBalanceException(
                    "Saldo insuficiente.");
        }
        validateCurrency(amount);
        this.balance = this.balance.subtract(amount);
    }

    private void validateCurrency(Money amount) {
        if (!this.balance.getCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }
}

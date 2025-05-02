package tech.xirius.payment.application.ports.in;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface RechargeWalletUseCase {
    void recharge(String userId, BigDecimal amount, Optional<UUID> paymentId);
}

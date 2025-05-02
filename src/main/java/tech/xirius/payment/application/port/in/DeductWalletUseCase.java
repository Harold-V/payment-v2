package tech.xirius.payment.application.port.in;

import java.math.BigDecimal;

public interface DeductWalletUseCase {
    void deduct(String userId, BigDecimal amount);
}

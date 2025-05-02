package tech.xirius.payment.application.port.in;

import tech.xirius.payment.infrastructure.web.dto.WalletBalanceResponse;

public interface GetWalletBalanceUseCase {
    WalletBalanceResponse getWalletBalance(String userId);
}

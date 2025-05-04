package tech.xirius.payment.application.port.in;

import tech.xirius.payment.infrastructure.web.dto.WalletBalanceResponse;

/**
 * Caso de uso para obtener el saldo de la billetera del usuario
 */
public interface GetWalletBalanceUseCase {
    WalletBalanceResponse getWalletBalance(String userId);
}

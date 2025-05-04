package tech.xirius.payment.application.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.GetWalletBalanceUseCase;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.infrastructure.web.dto.WalletBalanceResponse;

/**
 * Servicio de aplicación para consultar el saldo de la billetera de un usuario.
 */
@Service
@RequiredArgsConstructor
public class GetWalletBalanceService implements GetWalletBalanceUseCase {

    private final WalletRepositoryPort walletRepository;

    /**
     * Obtiene el saldo de la billetera del usuario.
     * 
     * @param userId ID del usuario propietario de la billetera.
     * @return Saldo de la billetera del usuario
     */
    @Override
    public WalletBalanceResponse getWalletBalance(String userId) {
        // Buscar la wallet por userId
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for userId: " + userId));

        // Crear respuesta
        return new WalletBalanceResponse(
                wallet.getUserId(),
                wallet.getBalance().getAmount(),
                wallet.getBalance().getCurrency());
    }

}

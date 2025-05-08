package tech.xirius.payment.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tech.xirius.payment.application.port.in.GetWalletTransactionsUseCase;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;

/**
 * Servicio de aplicación para consultar las transacciones de la wallet de un
 * usuario.
 */
@Service
@RequiredArgsConstructor
public class GetWalletTransactionsService implements GetWalletTransactionsUseCase {

    private final WalletRepositoryPort walletRepository;
    private final WalletTransactionRepositoryPort transactionRepository;

    /**
     * * Obtiene las transacciones de la wallet del usuario.
     * 
     * @param userId   ID del usuario propietario de la billetera.
     * @param pageable Información de paginación.
     * @return Lista de transacciones de la billetera del usuario.
     */
    @Override
    public Page<WalletTransaction> getTransactionsByUserId(String userId, Pageable pageable) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return transactionRepository.findAllByWalletId(wallet.getWalletId(), pageable);
    }
}

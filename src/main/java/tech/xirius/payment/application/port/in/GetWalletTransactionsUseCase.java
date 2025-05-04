package tech.xirius.payment.application.port.in;

import tech.xirius.payment.domain.model.WalletTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Caso de uso para obtener las transacciones de la billetera del usuario.
 */
public interface GetWalletTransactionsUseCase {
    Page<WalletTransaction> getTransactionsByUserId(String userId, Pageable pageable);
}

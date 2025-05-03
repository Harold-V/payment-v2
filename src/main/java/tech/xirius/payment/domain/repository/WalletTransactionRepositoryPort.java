package tech.xirius.payment.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tech.xirius.payment.domain.model.WalletTransaction;

public interface WalletTransactionRepositoryPort {
    void save(WalletTransaction transaction);

    Page<WalletTransaction> findAllByWalletId(UUID walletId, Pageable pageable);
}

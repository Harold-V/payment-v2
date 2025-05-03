package tech.xirius.payment.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;
import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;
import tech.xirius.payment.infrastructure.persistence.mapper.WalletTransactionMapper;

@RequiredArgsConstructor
@Component
public class JpaWalletTransactionAdapter implements WalletTransactionRepositoryPort {
    private final JpaWalletTransactionRepository transactionJpaRepository;
    private final WalletTransactionMapper transactionMapper;
    private final JpaWalletRepository walletJpaRepository;

    @Override
    public void save(WalletTransaction transaction) {
        WalletEntity walletEntity = walletJpaRepository.findById(transaction.getWalletId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Wallet not found for walletId: " + transaction.getWalletId()));

        // Mapear a entidad
        WalletTransactionEntity entity = transactionMapper.toEntity(transaction, walletEntity);

        // Guardar en la base de datos
        transactionJpaRepository.save(entity);
    }

    @Override
    public Page<WalletTransaction> findAllByWalletId(UUID walletId, Pageable pageable) {
        return transactionJpaRepository.findAllByWalletId(walletId, pageable)
                .map(transactionMapper::toDomain);
    }
}

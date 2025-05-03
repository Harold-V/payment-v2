package tech.xirius.payment.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;

public interface JpaWalletTransactionRepository extends JpaRepository<WalletTransactionEntity, UUID> {

    Page<WalletTransactionEntity> findAllByWalletId(UUID walletId, Pageable pageable);

}

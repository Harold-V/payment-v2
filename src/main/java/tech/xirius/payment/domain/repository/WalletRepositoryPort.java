package tech.xirius.payment.domain.repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import tech.xirius.payment.domain.model.Wallet;

public interface WalletRepositoryPort {
    Optional<Wallet> findByUserId(String userId);

    Optional<Wallet> findById(UUID walletId);

    Optional<BigDecimal> findBalanceByUserId(String userId);

    Wallet save(Wallet wallet);
}

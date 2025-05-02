package tech.xirius.payment.domain.repository;

import java.math.BigDecimal;
import java.util.Optional;

import tech.xirius.payment.domain.model.Wallet;

public interface WalletRepositoryPort {
    Optional<Wallet> findByUserId(String userId);

    Optional<BigDecimal> findBalanceByUserId(String userId);

    Wallet save(Wallet wallet);
}

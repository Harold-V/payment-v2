package tech.xirius.payment.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;
import tech.xirius.payment.infrastructure.persistence.mapper.WalletMapper;
import tech.xirius.payment.infrastructure.persistence.repository.JpaWalletRepository;

@RequiredArgsConstructor
@Component
public class JpaWalletRepositoryAdapter implements WalletRepositoryPort {

    private final JpaWalletRepository jpaWalletRepository;
    private final WalletMapper walletMapper;

    @Override
    public Optional<Wallet> findByUserId(String userId) {
        return jpaWalletRepository.findByUserId(userId)
                .map(walletMapper::toDomain);
    }

    @Override
    public Optional<BigDecimal> findBalanceByUserId(String userId) {
        return jpaWalletRepository.findBalanceByUserId(userId);
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity entity = walletMapper.toEntity(wallet);
        WalletEntity saved = jpaWalletRepository.save(entity);
        return walletMapper.toDomain(saved);
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        return jpaWalletRepository.findById(walletId)
                .map(walletMapper::toDomain);
    }

}

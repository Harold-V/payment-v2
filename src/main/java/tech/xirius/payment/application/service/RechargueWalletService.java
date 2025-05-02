package tech.xirius.payment.application.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.RechargeWalletUseCase;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class RechargueWalletService implements RechargeWalletUseCase {

    private final WalletRepositoryPort walletRepository;

    @Override
    public void recharge(String userId, BigDecimal amount, Optional<UUID> paymentId) {
        // Buscar la wallet por userId
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for userId: " + userId));

        // Crear objeto Money
        Money rechargeAmount = Money.of(amount, Currency.COP);

        // Recargar el saldo en dominio
        wallet.recharge(rechargeAmount);

        // Guardar wallet actualizada
        walletRepository.save(wallet);
    }

}

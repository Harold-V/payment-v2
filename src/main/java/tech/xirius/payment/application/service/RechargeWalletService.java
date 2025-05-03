package tech.xirius.payment.application.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.RechargeWalletUseCase;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.model.WalletTransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class RechargeWalletService implements RechargeWalletUseCase {

    private final WalletRepositoryPort walletRepository;
    private final WalletTransactionRepositoryPort walletTransactionRepository;

    @Override
    public void recharge(String userId, BigDecimal amount, UUID paymentId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElse(new Wallet(UUID.randomUUID(), userId,
                        new Money(BigDecimal.ZERO, Currency.COP)));

        BigDecimal previousBalance = wallet.getBalance().getAmount();
        wallet.recharge(new Money(amount, Currency.COP));
        walletRepository.save(wallet);

        if (paymentId != null) {
            walletTransactionRepository.save(new WalletTransaction(
                    UUID.randomUUID(),
                    wallet.getId(),
                    new Money(amount, Currency.COP),
                    WalletTransactionType.RECHARGE,
                    WalletTransactionStatus.APPROVED, // ES DE PRUEBA
                    ZonedDateTime.now(),
                    paymentId,
                    previousBalance,
                    wallet.getBalance().getAmount()));

        } else {
            walletTransactionRepository.save(new WalletTransaction(
                    UUID.randomUUID(),
                    wallet.getId(),
                    new Money(amount, Currency.COP),
                    WalletTransactionType.RECHARGE,
                    WalletTransactionStatus.APPROVED, // ES DE PRUEBA
                    ZonedDateTime.now(),
                    null, // Las recargas deberian tener un paymentId asociado pero estamos en un
                          // ambiente de pruebas
                    previousBalance,
                    wallet.getBalance().getAmount()));
        }

    }

}

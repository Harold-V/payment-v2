package tech.xirius.payment.application.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.DeductWalletUseCase;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;

/**
 * Servicio de aplicación para deducir dinero de la billetera del usuario.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DeductWalletService implements DeductWalletUseCase {

    private final WalletRepositoryPort walletRepository;
    private final WalletTransactionRepositoryPort walletTransactionRepository;

    /**
     * Deducir el saldo de la billetera del usuario.
     * 
     * @param userId ID del usuario propietario de la billetera.
     * @param amount Monto a deducir de la billetera.
     */
    @Override
    public void deduct(String userId, BigDecimal amount) {
        // Buscar la wallet por userId
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for userId: " + userId));
        // Crear objeto Money

        BigDecimal previousBalance = wallet.getBalance().getAmount();

        // Recargar el saldo en dominio
        wallet.deduct(Money.of(amount, Currency.COP));

        // Guardar wallet actualizada
        walletRepository.save(wallet);

        // Guardar transacción en la base de datos
        walletTransactionRepository.save(new WalletTransaction(
                UUID.randomUUID(),
                wallet.getId(),
                new Money(amount, Currency.COP),
                WalletTransactionType.DEDUCT,
                TransactionStatus.APPROVED,
                ZonedDateTime.now(),
                previousBalance,
                wallet.getBalance().getAmount()));
    }

}

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
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;

/*
 * Servicio de aplicación para recargar dinero en la billetera del usuario.
 * Este servicio se encarga de gestionar la recarga de dinero en la billetera del usuario.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RechargeWalletService implements RechargeWalletUseCase {

    private final WalletRepositoryPort walletRepository;
    private final WalletTransactionRepositoryPort walletTransactionRepository;

    /**
     * Recarga el saldo de la billetera del usuario.
     * 
     * @param userId    ID del usuario propietario de la billetera.
     * 
     * @param amount    Monto a recargar en la billetera.
     * 
     * @param paymentId ID de la transacción de pago.
     */
    @Override
    public void recharge(String userId, BigDecimal amount, UUID paymentId) {
        // Buscar la wallet por userId
        // Si no existe, crear una nueva wallet con saldo 0
        // y asignar el userId
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElse(new Wallet(UUID.randomUUID(), userId,
                        new Money(BigDecimal.ZERO, Currency.COP)));
        // Capturar el saldo anterior
        // Crear objeto Money
        // Recargar el saldo en dominio
        BigDecimal previousBalance = wallet.getBalance().getAmount();
        wallet.recharge(new Money(amount, Currency.COP));
        walletRepository.save(wallet);

        // Guardar transacción en la base de datos
        if (paymentId != null) {
            walletTransactionRepository.save(new WalletTransaction(
                    UUID.randomUUID(),
                    wallet.getId(),
                    new Money(amount, Currency.COP),
                    WalletTransactionType.RECHARGE,
                    TransactionStatus.APPROVED, // ES DE PRUEBA
                    ZonedDateTime.now(),
                    previousBalance,
                    wallet.getBalance().getAmount()));

        } else {
            walletTransactionRepository.save(new WalletTransaction(
                    UUID.randomUUID(),
                    wallet.getId(),
                    new Money(amount, Currency.COP),
                    WalletTransactionType.RECHARGE,
                    TransactionStatus.APPROVED, // ES DE PRUEBA
                    ZonedDateTime.now(),
                    previousBalance,
                    wallet.getBalance().getAmount()));
        }

    }

}

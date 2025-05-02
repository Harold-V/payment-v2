package tech.xirius.payment.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.DeductWalletUseCase;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class DeductWalletService implements DeductWalletUseCase {

    private final WalletRepositoryPort walletRepository;

    @Override
    public void deduct(String userId, BigDecimal amount) {
        // Buscar la wallet por userId
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found for userId: " + userId));
        // Crear objeto Money
        Money deductAmount = Money.of(amount, Currency.COP);

        // Recargar el saldo en dominio
        wallet.deduct(deductAmount);

        // Guardar wallet actualizada
        walletRepository.save(wallet);

    }

}

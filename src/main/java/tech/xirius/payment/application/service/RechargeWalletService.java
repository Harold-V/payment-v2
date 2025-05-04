package tech.xirius.payment.application.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tech.xirius.payment.application.port.in.RechargeWalletUseCase;
import tech.xirius.payment.application.port.out.PaymentGatewayPort;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Payment;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import tech.xirius.payment.domain.repository.PaymentRepositoryPort;
import tech.xirius.payment.domain.repository.WalletRepositoryPort;
import tech.xirius.payment.domain.repository.WalletTransactionRepositoryPort;
import tech.xirius.payment.infrastructure.web.dto.RechargeRequest;
import tech.xirius.payment.infrastructure.web.dto.PaymentResponse;

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
    private final PaymentGatewayPort paymentGatewayPort;
    private final PaymentRepositoryPort paymentRepositoryPort;

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
    public void recharge(RechargeRequest paymentRequest) {

        Wallet wallet = walletRepository.findByUserId(paymentRequest.userId())
                .orElse(new Wallet(UUID.randomUUID(), paymentRequest.userId(),
                        new Money(BigDecimal.ZERO, Currency.COP)));

        BigDecimal previousBalance = wallet.getBalance().getAmount();

        WalletTransaction walletTransaction = new WalletTransaction(
                UUID.randomUUID(),
                wallet.getId(),
                new Money(paymentRequest.amount(), Currency.COP),
                WalletTransactionType.RECHARGE,
                TransactionStatus.PENDING,
                ZonedDateTime.now(),
                previousBalance,
                wallet.getBalance().getAmount());

        PaymentResponse paymentResponse = paymentGatewayPort.processPayment(paymentRequest);

        // Evaluar el estado de la respuesta del pago y actualizar la transacción
        switch (paymentResponse.state()) {
            case APPROVED:
                walletTransaction.setStatus(TransactionStatus.APPROVED);
                wallet.recharge(new Money(paymentRequest.amount(), Currency.COP));
                walletRepository.save(wallet); // Guardar el nuevo saldo en la billetera
                break;
            case FAILED:
                walletTransaction.setStatus(TransactionStatus.FAILED);
                break;
            case REJECTED:
                walletTransaction.setStatus(TransactionStatus.REJECTED);
                break;
            default:
                walletTransaction.setStatus(TransactionStatus.PENDING);
                break;
        }

        Payment payment = new Payment(
                UUID.randomUUID(),
                walletTransaction.getId(),
                paymentResponse.orderId(),
                paymentResponse.transactionId(),
                paymentResponse.state(),
                paymentRequest.paymentProvider(),
                paymentRequest.paymentMethod()

        );

        walletTransactionRepository.save(walletTransaction);
        paymentRepositoryPort.save(payment);

    }

}

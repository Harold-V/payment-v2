package tech.xirius.payment.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.xirius.payment.domain.model.PaymentMethod;
import tech.xirius.payment.domain.model.PaymentProvider;
import tech.xirius.payment.domain.model.TransactionStatus;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "transaction_id")
})
public class PaymentEntity {

    @Id
    @Column(name = "payment_id", nullable = false, updatable = false)
    private String id; // ID de la transacción de pago

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Wallet_transaction_id", nullable = false)
    private WalletTransactionEntity walletTransaction; // ID de la transacción en la billetera del usuario

    @Column(name = "order_id", nullable = false)
    private String orderId; // ID de la orden asociada al pago

    @Column(name = "transaction_id", nullable = false)
    private String transactionId; // ID específico de la transacción en el sistema de la pasarela de pago

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private TransactionStatus paymentStatus; // Estado de la transacción (autorizado, procesado, fallido, etc.)

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider", nullable = false)
    private PaymentProvider paymentProvider; // Proveedor de pago (PayU, MercadoPago, etc.)

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod; // Método de pago (tarjeta, PSE, etc.)

}

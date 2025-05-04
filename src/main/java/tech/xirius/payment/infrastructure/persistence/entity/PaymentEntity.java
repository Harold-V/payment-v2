package tech.xirius.payment.infrastructure.persistence.entity;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa un pago realizado por un usuario")
public class PaymentEntity {

    @Id
    @Column(name = "payment_id", nullable = false, updatable = false)
    @Schema(description = "ID único de la transacción de pago", required = true)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Wallet_transaction_id", nullable = false)
    @Schema(description = "ID de la transacción de la billetera asociada al pago", required = true)
    private WalletTransactionEntity walletTransaction;

    @Column(name = "order_id", nullable = false)
    @Schema(description = "ID de la orden asociada al pago", required = true)
    private String orderId;

    @Column(name = "transaction_id", nullable = false)
    @Schema(description = "ID de la transacción de pago proporcionada por el proveedor de pago", required = true)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    @Schema(description = "Estado de la transacción de pago (APPROVED,FAILED,REJECTED,PENDING)", required = true)
    private TransactionStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider", nullable = false)
    @Schema(description = "Proveedor de pago (PayU, MercadoPago, etc.) utilizado para la transacción", required = true)
    private PaymentProvider paymentProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @Schema(description = "Método de pago utilizado (tarjeta, PSE, etc.)", required = true)
    private PaymentMethod paymentMethod;

}

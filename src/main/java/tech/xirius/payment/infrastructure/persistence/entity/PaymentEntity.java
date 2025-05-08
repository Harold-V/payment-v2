package tech.xirius.payment.infrastructure.persistence.entity;

import java.util.UUID;

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

/**
 * Entidad que representa una transacción de pago en la base de datos.
 * Esta clase mapea la tabla payments en la base de datos y almacena los
 * detalles de un pago
 * realizado, como el ID de la transacción, el estado del pago, el proveedor de
 * pago y el método de pago.
 * 
 * <p>
 * Se utiliza para gestionar los pagos realizados mediante diferentes
 * proveedores y métodos de pago.
 * </p>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "transaction_id")
})
public class PaymentEntity {

    /**
     * ID único de la transacción de pago.
     * Este campo es la clave primaria en la tabla de pagos.
     */
    @Id
    @Column(name = "payment_id", nullable = false, updatable = false)
    private UUID paymentId;

    /**
     * Transacción asociada al pago en la billetera del usuario.
     * Este campo hace referencia a la entidad {@link WalletTransactionEntity}.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Wallet_transaction_id", nullable = false)
    private WalletTransactionEntity walletTransaction;

    /**
     * ID de la orden asociada al pago.
     * Este campo es utilizado para identificar la orden que se paga mediante esta
     * transacción.
     */
    @Column(name = "order_id", nullable = false)
    private String orderId;

    /**
     * ID específico de la transacción en el sistema de la pasarela de pago.
     * Este campo es utilizado para identificar la transacción dentro de la pasarela
     * de pagos.
     */
    @Column(name = "gateway_transaction_id", nullable = false)
    private String gatewayTransactionId;

    /**
     * Estado de la transacción de pago.
     * Este campo indica si el pago fue APROBADO, FALLIDO, RECHAZADO, PENDIENTE.
     * Utiliza el valor del {@link TransactionStatus}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private TransactionStatus paymentStatus;

    /**
     * Proveedor de pago utilizado para la transacción.
     * Este campo se utiliza para identificar el sistema de pagos que procesó la
     * transacción, como PayU o MercadoPago.
     * Utiliza el valor del {@link PaymentProvider}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider", nullable = false)
    private PaymentProvider paymentProvider; // Proveedor de pago (PayU, MercadoPago, etc.)

    /**
     * Método de pago utilizado para la transacción.
     * Este campo se utiliza para identificar el método de pago, como tarjeta de
     * crédito, débito, PSE, etc.
     * Utiliza el valor del {@link PaymentMethod}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod; // Método de pago (tarjeta, PSE, etc.)
}

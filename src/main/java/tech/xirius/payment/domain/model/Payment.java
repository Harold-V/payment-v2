package tech.xirius.payment.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa una transacción de pago realizada por un usuario.
 * <p>
 * Esta clase encapsula toda la información relevante relacionada con un pago,
 * incluyendo sus identificadores en diferentes sistemas, estado y método
 * utilizado.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Payment {

    /**
     * Identificador único de la transacción de pago en el sistema.
     */
    private UUID paymentId;

    /**
     * Identificador único de la transacción en la billetera del usuario.
     * <p>
     * Este ID permite relacionar el pago con la operación correspondiente
     * en el sistema de transacciones en la billetera.
     * </p>
     */
    private WalletTransaction walletTransaction;

    /**
     * Identificador de la orden asociada al pago.
     * <p>
     * Permite vincular la transacción con la orden de compra o servicio
     * que el usuario está pagando.
     * </p>
     */
    private String orderId;

    /**
     * Identificador específico de la transacción en el sistema de la pasarela de
     * pago.
     * <p>
     * Este es el ID que asigna la pasarela de pago externa (como PayU, MercadoPago)
     * </p>
     */
    private String gatewayTransactionId;

    /**
     * Estado actual de la transacción de pago.
     * <p>
     * Indica si el pago ha sido Aprobado, Fallido, rechazado o Pendiente.
     * </p>
     * 
     * @see TransactionStatus
     */
    private TransactionStatus paymentStatus;

    /**
     * Proveedor de servicios de pago utilizado para la transacción.
     * <p>
     * Especifica qué pasarela de pago procesó la transacción (como PayU,
     * MercadoPago).
     * </p>
     * 
     * @see PaymentProvider
     */
    private PaymentProvider paymentProvider;

    /**
     * Método de pago utilizado para completar la transacción.
     * <p>
     * Especifica el instrumento de pago seleccionado por el usuario
     * (como tarjeta de crédito o debito, PSE).
     * </p>
     * 
     * @see PaymentMethod
     */
    private PaymentMethod paymentMethod;
}
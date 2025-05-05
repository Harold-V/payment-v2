package tech.xirius.payment.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * Representa una transacción de pago realizada por un usuario.
*/
@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private UUID id; // ID de la transacción de pago
    private UUID walletTransactionId; // ID de la transacción en la billetera del usuario
    private String orderId; // ID de la orden asociada al pago
    private String transactionId; // ID específico de la transacción en el sistema de la pasarela de pago
    private TransactionStatus paymentStatus; // Estado de la transacción (autorizado, procesado, fallido, etc.)
    private PaymentProvider paymentProvider; // Proveedor de pago (PayU, MercadoPago, etc.)
    private PaymentMethod paymentMethod; // Método de pago (tarjeta, PSE, etc.)

}

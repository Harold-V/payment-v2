package tech.xirius.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Representa una transacción realizada en la billetera del usuario.
 */
@Getter
@AllArgsConstructor
public class WalletTransaction {

    private final UUID id; // ID de la transacción

    private final UUID walletId; // ID de la billetera asociada a la transacción

    private final Money amount; // Monto de la transacción

    private final WalletTransactionType type; // Tipo de transacción (recarga o deducción)

    private final TransactionStatus status; // Estado de la transacción (aprobada, fallida, rechazada, pendiente)

    private final ZonedDateTime timestamp; // Fecha y hora de la transacción

    private final BigDecimal previousBalance; // Saldo anterior de la billetera antes de la transacción

    private final BigDecimal newBalance; // Saldo nuevo de la billetera después de la transacción

}

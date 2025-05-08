package tech.xirius.payment.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.xirius.payment.domain.model.TransactionStatus;
import tech.xirius.payment.domain.model.WalletTransactionType;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Representa una transacción en la billetera del usuario en la base de datos.
 * Esta clase mapea la tabla wallet_transactions en la base de datos y
 * almacena los detalles de una transacción de billetera, como el ID de la
 * transacción, el tipo de transacción, el estado, el saldo antes y después de
 * la transacción, y el monto asociado a la
 * transacción.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet_transactions")
public class WalletTransactionEntity {

    /**
     * ID único de la transacción de billetera.
     * Este campo es la clave primaria en la tabla de transacciones de billetera.
     */
    @Id
    @Column(name = "wallet_transaction_id", nullable = false, updatable = false, unique = true)
    private UUID walletTransactionId;

    /**
     * La billetera asociada con la transacción.
     * Este campo establece una relación con la entidad {@link WalletEntity}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity wallet;

    /**
     * Monto asociado a la transacción, que está embebido en la entidad.
     * Este campo está representado por la clase {@link MoneyEmbedded}.
     */
    @Column(name = "money", nullable = false, precision = 38, scale = 2)
    @Embedded
    private MoneyEmbedded amount;

    /**
     * Tipo de la transacción de billetera, que puede ser de recarga o deducción.
     * Utiliza el valor de {@link WalletTransactionType}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WalletTransactionType type;

    /**
     * Estado de la transacción, como aprobada, fallida, rechazada o pendiente.
     * Utiliza el valor de {@link TransactionStatus}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    /**
     * Fecha y hora de la transacción.
     * Este campo almacena el timestamp de la transacción.
     */
    @Column(name = "timestamp", nullable = false)
    private ZonedDateTime timestamp;

    /**
     * El saldo de la billetera antes de realizar la transacción.
     * Este campo representa el saldo disponible en la billetera antes de la
     * transacción.
     */
    @Column(name = "previous_balance", nullable = false, precision = 38, scale = 2)
    private BigDecimal previousBalance;

    /**
     * El saldo de la billetera después de realizar la transacción.
     * Este campo representa el saldo disponible en la billetera después de la
     * transacción.
     */
    @Column(name = "new_balance", nullable = false, precision = 38, scale = 2)
    private BigDecimal newBalance;
}

package tech.xirius.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Representa una transacción realizada en la billetera del usuario.
 * <p>
 * Esta clase encapsula los detalles de una operación financiera dentro de la
 * billetera,
 * incluyendo información sobre el monto, tipo de transacción, estado, y el
 * impacto
 * en el saldo de la billetera.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
public class WalletTransaction {

    /**
     * Identificador único de la transacción.
     * <p>
     * Este campo es inmutable una vez creada la transacción.
     * </p>
     */
    private final UUID walletTransactionId;

    /**
     * Identificador de la billetera asociada a esta transacción.
     * <p>
     * Este campo es inmutable y establece la relación entre la transacción
     * y la billetera del usuario.
     * </p>
     */
    private final Wallet wallet;

    /**
     * Monto de la transacción.
     * <p>
     * Representa la cantidad de dinero involucrada en la operación,
     * incluyendo el valor y el tipode moneda.
     * </p>
     * 
     * @see Money
     */
    private final Money amount;

    /**
     * Tipo de transacción realizada.
     * <p>
     * Especifica si es una recarga (crédito) o una deducción (débito) de fondos.
     * </p>
     * 
     * @see WalletTransactionType
     */
    private final WalletTransactionType type;

    /**
     * Estado actual de la transacción.
     * <p>
     * Indica si la transacción ha sido aprobada, fallida, rechazada o
     * está pendiente de procesamiento.
     * </p>
     * <p>
     * Este es uno de los pocos campos que pueden ser modificados después de
     * la creación de la transacción, reflejando su progreso en el sistema.
     * </p>
     * 
     * @see TransactionStatus
     */
    private TransactionStatus status;

    /**
     * Fecha y hora exacta en que se realizó la transacción.
     * <p>
     * Este valor incluye información de zona horaria y es inmutable.
     * </p>
     */
    private final ZonedDateTime timestamp;

    /**
     * Saldo de la billetera antes de aplicar esta transacción.
     * <p>
     * Este campo es inmutable y sirve como referencia histórica del
     * estado de la billetera en el momento de iniciar la transacción.
     * </p>
     */
    private final BigDecimal previousBalance;

    /**
     * Saldo de la billetera después de aplicar esta transacción.
     * <p>
     * Este campo puede ser actualizado si la transacción se procesa en
     * múltiples etapas o si hay ajustes posteriores.
     * </p>
     */
    private BigDecimal newBalance;
}
package tech.xirius.payment.infrastructure.adapter.payu.dto;

/**
 * Representa una transacción en la pasarela de pagos.
 * Esta clase se utiliza para almacenar el identificador único de la transacción
 * en la pasarela de pagos,
 * que permite rastrear y verificar la transacción procesada.
 * 
 * <p>
 * Contiene el ID de transacción proporcionado por la pasarela de pagos, el cual
 * es utilizado
 * para realizar consultas y operaciones relacionadas con la transacción.
 * </p>
 */
public record GatewayTransaction(
        /**
         * Identificador único de la transacción proporcionado por la pasarela de pagos.
         * Este campo es necesario para realizar un seguimiento de la transacción en el
         * sistema de pago.
         */
        String gatewayTransactionId) {

}

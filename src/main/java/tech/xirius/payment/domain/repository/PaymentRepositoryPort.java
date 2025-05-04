package tech.xirius.payment.domain.repository;

import java.util.Optional;

import tech.xirius.payment.domain.model.Payment;

/**
 * Interfaz del repositorio de pagos que define operaciones
 * para persistir y recuperar objetos Payment
 */
public interface PaymentRepositoryPort {
    /**
     * Guarda un pago en el repositorio
     * 
     * @param payment el pago a guardar
     */
    void save(Payment payment);

    /**
     * Busca un pago por su ID de transacción
     * 
     * @param transactionId el ID de transacción proporcionado por el proveedor de
     *                      pago
     * @return un Optional que contiene el pago si existe
     */
    Optional<Payment> findByTransactionId(String transactionId);

}
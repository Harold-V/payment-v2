package tech.xirius.payment.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;

/**
 * Repositorio JPA para la entidad {@link PaymentEntity}.
 * Esta interfaz extiende {@link JpaRepository}, proporcionando métodos para
 * realizar operaciones CRUD
 * en la tabla payments de la base de datos, además de una consulta
 * personalizada para buscar pagos por ID de transacción.
 * 
 * <p>
 * Este repositorio se utiliza para interactuar con la base de datos, realizando
 * operaciones de acceso a los datos de pagos.
 * </p>
 */
public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    /**
     * Busca un pago en la base de datos utilizando el ID de la transacción.
     * 
     * @param transactionId El ID de la transacción de pago que se desea buscar.
     * @return Un Optional que contiene el {@link PaymentEntity} encontrado,
     *         si existe.
     */
    Optional<PaymentEntity> findByTransactionId(String transactionId);

}

package tech.xirius.payment.infrastructure.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.domain.model.Payment;
import tech.xirius.payment.domain.repository.PaymentRepositoryPort;
import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;
import tech.xirius.payment.infrastructure.persistence.mapper.PaymentMapper;
import tech.xirius.payment.infrastructure.persistence.repository.JpaPaymentRepository;

/**
 * Adaptador de repositorio de pagos que implementa la interfaz
 * {@link PaymentRepositoryPort}.
 * Esta clase se encarga de la persistencia de pagos en la base de datos
 * utilizando JPA y un repositorio de tipo {@link JpaPaymentRepository}.
 * 
 * <p>
 * Proporciona los métodos necesarios para guardar pagos y consultar pagos por
 * su ID de transacción.
 * </p>
 */
@RequiredArgsConstructor
@Component
public class JpaPaymentRepositoryAdapter implements PaymentRepositoryPort {

    /**
     * Repositorio JPA utilizado para interactuar con la base de datos.
     */
    private final JpaPaymentRepository jpaRepository;

    /**
     * Mapeador utilizado para convertir entre entidades de la base de datos y
     * objetos de dominio.
     */
    private final PaymentMapper paymentMapper;

    /**
     * Guarda un pago en la base de datos.
     * 
     * @param payment El objeto {@link Payment} que se desea guardar.
     */
    @Override
    public void save(Payment payment) {
        PaymentEntity entity = paymentMapper.toEntity(payment); // Mapea el objeto de dominio a la entidad JPA
        PaymentEntity saved = jpaRepository.save(entity); // Guarda la entidad en la base de datos
        paymentMapper.toDomain(saved); // Mapea la entidad guardada de vuelta a un objeto de dominio
    }

    /**
     * Busca un pago en la base de datos utilizando el ID de la transacción.
     * 
     * @param transactionId El ID de la transacción que se utilizará para buscar el
     *                      pago.
     * @return Un Optional que contiene el pago encontrado, si existe.
     */
    @Override
    public Optional<Payment> findByTransactionId(String transactionId) {
        return jpaRepository.findByTransactionId(transactionId) // Busca la entidad por el ID de la transacción
                .map(paymentMapper::toDomain); // Mapea la entidad a un objeto de dominio y lo devuelve en un Optional
    }

}

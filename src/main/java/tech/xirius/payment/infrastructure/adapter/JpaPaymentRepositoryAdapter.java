package tech.xirius.payment.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tech.xirius.payment.domain.model.Payment;
import tech.xirius.payment.domain.repository.PaymentRepositoryPort;
import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;
import tech.xirius.payment.infrastructure.persistence.mapper.PaymentMapper;
import tech.xirius.payment.infrastructure.persistence.repository.JpaPaymentRepository;

@RequiredArgsConstructor
@Component
public class JpaPaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final JpaPaymentRepository jpaRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public void save(Payment payment) {
        PaymentEntity entity = paymentMapper.toEntity(payment);
        PaymentEntity saved = jpaRepository.save(entity);
        paymentMapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findByTransactionId(String transactionId) {
        return jpaRepository.findByTransactionId(transactionId)
                .map(paymentMapper::toDomain);
    }

}

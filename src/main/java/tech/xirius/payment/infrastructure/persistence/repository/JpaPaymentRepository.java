package tech.xirius.payment.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {

    Optional<PaymentEntity> findByTransactionId(String transactionId);

}

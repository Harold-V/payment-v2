package tech.xirius.payment.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {

}

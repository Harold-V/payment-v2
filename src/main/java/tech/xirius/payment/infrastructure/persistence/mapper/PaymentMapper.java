package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import tech.xirius.payment.domain.model.Payment;
import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toDomain(PaymentEntity entity);

    PaymentEntity toEntity(Payment domain);

}

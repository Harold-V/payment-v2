package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;

@Mapper(uses = { WalletMapper.class }, componentModel = "spring")
public interface WalletTransactionMapper {

    WalletTransaction toDomain(WalletTransactionEntity entity);

    WalletTransactionEntity toEntity(WalletTransaction domain);

}
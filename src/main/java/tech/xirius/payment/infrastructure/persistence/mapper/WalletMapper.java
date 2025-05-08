package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    Wallet toDomain(WalletEntity entity);

    WalletEntity toEntity(Wallet domain);

}
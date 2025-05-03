package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;
import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { WalletMapper.class })
public interface WalletTransactionMapper {

    WalletTransactionMapper INSTANCE = Mappers.getMapper(WalletTransactionMapper.class);

    @Mapping(target = "amount", expression = "java(toMoney(entity.getAmount(), entity.getCurrency()))")
    @Mapping(target = "walletId", expression = "java(entity.getWallet().getWalletId())")
    WalletTransaction toDomain(WalletTransactionEntity entity);

    @Mapping(target = "wallet", source = "walletEntity")
    @Mapping(target = "amount", source = "domain.amount.amount")
    @Mapping(target = "currency", source = "domain.amount.currency")
    WalletTransactionEntity toEntity(WalletTransaction domain, WalletEntity walletEntity);

    default Money toMoney(java.math.BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }
}
package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Currency;

import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {

    @Mapping(target = "balance", expression = "java(toMoney(entity.getBalance(), entity.getCurrency()))")
    Wallet toDomain(WalletEntity entity);

    @Mapping(target = "balance", source = "domain.balance.amount")
    @Mapping(target = "currency", source = "domain.balance.currency")
    WalletEntity toEntity(Wallet domain);

    default Money toMoney(java.math.BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }
}
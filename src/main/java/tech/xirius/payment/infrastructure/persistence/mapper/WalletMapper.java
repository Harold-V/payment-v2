package tech.xirius.payment.infrastructure.persistence.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(source = "walletId", target = "walletId")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "bigDecimalToMoney")
    Wallet toDomain(WalletEntity entity);

    @Mapping(source = "walletId", target = "walletId")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "moneyToBigDecimal")
    WalletEntity toEntity(Wallet wallet);

    @Named("bigDecimalToMoney")
    default Money bigDecimalToMoney(BigDecimal amount) {
        return Money.of(amount, Currency.COP);
    }

    @Named("moneyToBigDecimal")
    default BigDecimal moneyToBigDecimal(Money money) {
        return money.getAmount();
    }

}

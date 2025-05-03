package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tech.xirius.payment.domain.model.Currency;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.Wallet;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = ".", target = "balance", qualifiedByName = "toMoney")
    Wallet toDomain(WalletEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "balance.amount", target = "balance")
    @Mapping(source = "balance.currency", target = "currency")
    WalletEntity toEntity(Wallet domain);

    List<Wallet> toDomainList(List<WalletEntity> entities);

    List<WalletEntity> toEntityList(List<Wallet> domains);

    @Named("toMoney")
    default Money toMoney(WalletEntity entity) {
        return new Money(entity.getBalance(), entity.getCurrency());
    }

    @Named("toAmount")
    default BigDecimal toAmount(Money money) {
        return money.getAmount();
    }

    @Named("toCurrency")
    default Currency toCurrency(Money money) {
        return money.getCurrency();
    }
}
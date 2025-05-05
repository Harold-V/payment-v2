package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tech.xirius.payment.domain.model.Money;
import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;
import tech.xirius.payment.infrastructure.persistence.entity.WalletEntity;
import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;

import java.util.List;
import java.util.UUID;

@Mapper(uses = { WalletMapper.class }, componentModel = "spring")
public interface WalletTransactionMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "wallet.id", target = "walletId")
    @Mapping(source = ".", target = "amount", qualifiedByName = "toMoney")
    WalletTransaction toDomain(WalletTransactionEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "wallet", source = "walletId", qualifiedByName = "mapWalletId")
    @Mapping(source = "amount.amount", target = "amount")
    @Mapping(source = "amount.currency", target = "currency")
    WalletTransactionEntity toEntity(WalletTransaction domain);

    List<WalletTransaction> toDomainList(List<WalletTransactionEntity> entities);

    List<WalletTransactionEntity> toEntityList(List<WalletTransaction> domains);

    @Named("toMoney")
    default Money toMoney(WalletTransactionEntity entity) {
        return new Money(entity.getAmount(), entity.getCurrency());
    }

    @Named("mapWalletId")
    default WalletEntity mapWalletId(UUID walletId) {
        if (walletId == null) {
            return null;
        }
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(walletId);
        return walletEntity;
    }

    @Named("mapPaymentId")
    default PaymentEntity mapPaymentId(String paymentId) {
        if (paymentId == null) {
            return null;
        }
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(paymentId);
        return paymentEntity;
    }
}
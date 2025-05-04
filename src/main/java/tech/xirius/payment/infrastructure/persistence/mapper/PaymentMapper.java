package tech.xirius.payment.infrastructure.persistence.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import tech.xirius.payment.domain.model.Payment;
import tech.xirius.payment.infrastructure.persistence.entity.PaymentEntity;
import tech.xirius.payment.infrastructure.persistence.entity.WalletTransactionEntity;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "walletTransaction.id", target = "walletTransactionId")
    Payment toDomain(PaymentEntity entity);

    @Mapping(source = "walletTransactionId", target = "walletTransaction", qualifiedByName = "mapWalletTransactionId")
    PaymentEntity toEntity(Payment domain);

    @Named("mapWalletTransactionId")
    default WalletTransactionEntity mapWalletTransactionId(UUID walletTransactionId) {
        if (walletTransactionId == null) {
            return null;
        }
        WalletTransactionEntity entity = new WalletTransactionEntity();
        entity.setId(walletTransactionId);
        return entity;
    }
}

package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.infrastructure.web.dto.WalletTransactionResponse;

@Mapper(componentModel = "spring")
public interface WalletTransactionResponseMapper {

    @Mappings({
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "amount", source = "transaction.amount.amount"),
            @Mapping(target = "currency", source = "transaction.amount.currency"),
            @Mapping(target = "type", source = "transaction.type"),
            @Mapping(target = "status", source = "transaction.status"),
            @Mapping(target = "timestamp", source = "transaction.timestamp"),
            @Mapping(target = "previousBalance", source = "transaction.previousBalance"),
            @Mapping(target = "newBalance", source = "transaction.newBalance"),
            @Mapping(target = "id", source = "transaction.id")
    })
    WalletTransactionResponse toResponse(WalletTransaction transaction, String userId);

}

package tech.xirius.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import tech.xirius.payment.domain.model.WalletTransaction;
import tech.xirius.payment.infrastructure.web.dto.WalletTransactionResponse;

@Mapper(componentModel = "spring")
public interface WalletTransactionResponseMapper {

    @Mappings({

            @Mapping(target = "id", source = "transaction.walletTransactionId"),
            @Mapping(target = "walletId", source = "transaction.wallet.walletId"),
    })
    WalletTransactionResponse toResponse(WalletTransaction transaction, String userId);

}

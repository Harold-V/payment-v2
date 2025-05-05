package tech.xirius.payment.infrastructure.adapter.payu.dto;

import java.math.BigDecimal;

import tech.xirius.payment.domain.model.PaymentMethod;
import tech.xirius.payment.domain.model.PaymentProvider;

public record RechargeRequest(
        String userId,
        BigDecimal amount,
        PaymentProvider paymentProvider,
        PaymentMethod paymentMethod,
        String description,
        String fullName,
        String emailAddress,
        String contactPhone,
        String dniNumber,
        String dniType,
        ShippingAddress shippingAddress,
        // Atributos de la tarjeta de credito
        String creditCardNumber,
        String securityCode,
        String expirationDate,
        String cardHolderName,
        // Atributos Necesarios Para Pagar por pse
        String userType

) {

}
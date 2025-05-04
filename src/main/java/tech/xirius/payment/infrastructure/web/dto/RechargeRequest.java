package tech.xirius.payment.infrastructure.web.dto;

import java.math.BigDecimal;

import tech.xirius.payment.domain.model.PaymentMethod;
import tech.xirius.payment.domain.model.PaymentProvider;

public record RechargeRequest(
                String userId,
                BigDecimal amount,
                PaymentProvider paymentProvider,
                PaymentMethod paymentMethod,
                String description,
                String notifyUrl,
                String fullName,
                String emailAddress,
                String contactPhone,
                String dniNumber,
                ShippingAddress shippingAddress,
                String creditCardNumber,
                String securityCode,
                String expirationDate,
                String cardHolderName) {

}
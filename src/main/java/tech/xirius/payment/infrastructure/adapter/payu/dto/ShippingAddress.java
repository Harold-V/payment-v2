package tech.xirius.payment.infrastructure.adapter.payu.dto;

public record ShippingAddress(
        String street1,
        String street2,
        String city,
        String state,
        String country,
        String postalCode,
        String phone) {

}

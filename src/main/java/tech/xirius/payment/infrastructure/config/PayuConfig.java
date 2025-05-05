package tech.xirius.payment.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "payu")
public class PayuConfig {

    private Account account;
    private Merchant merchant;
    private Api api;
    private Transaction transaction;

    @Getter
    @Setter
    public static class Account {
        private String id;
    }

    @Getter
    @Setter
    public static class Merchant {
        private String id;
    }

    @Getter
    @Setter
    public static class Api {
        private String login;
        private String key;
        private String url;
    }

    @Getter
    @Setter
    public static class Transaction {
        private String language;
        private String paymentCountry;
        private boolean test;

    }

}

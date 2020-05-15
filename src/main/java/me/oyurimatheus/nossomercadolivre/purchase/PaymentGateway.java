package me.oyurimatheus.nossomercadolivre.purchase;


import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

import static org.springframework.util.Assert.notNull;

enum PaymentGateway {

    PAYPAL {

        @Override
        String paymentUrl(@NotNull Purchase purchase, @URL String redirectUrl) {
            notNull(purchase, "purchase must not be null");
            return String.format("paypal.com/%s?redirectUrl=%s", purchase.getId(), redirectUrl);
        }
    },
    PAGSEGURO {
        @Override
        String paymentUrl(@NotNull Purchase purchase, @URL String redirectUrl) {
            notNull(purchase, "purchase must not be null");

            return String.format("pagseguro.com?returnId=%s&redirectUrl=%s", purchase.getId(), redirectUrl);
        }
    };

    abstract String paymentUrl(@NotNull Purchase purchase, @URL String redirectUrl);
}

package me.oyurimatheus.nossomercadolivre.purchase;


import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

import static me.oyurimatheus.nossomercadolivre.purchase.Status.ERRO;
import static me.oyurimatheus.nossomercadolivre.purchase.Status.SUCESSO;
import static org.springframework.util.Assert.notNull;

enum PaymentGateway {

    PAYPAL {

        @Override
        String paymentUrl(@NotNull Purchase purchase, @URL String redirectUrl) {
            notNull(purchase, "purchase must not be null");
            return String.format("paypal.com/%s?redirectUrl=%s", purchase.getId(), redirectUrl);
        }

        @Override
        public Status status(Payment payment) {
            if (payment.getStatus().equals("1")) {
                return SUCESSO;
            }

            return ERRO;
        }
    },
    PAGSEGURO {
        @Override
        String paymentUrl(@NotNull Purchase purchase, @URL String redirectUrl) {
            notNull(purchase, "purchase must not be null");

            return String.format("pagseguro.com?returnId=%s&redirectUrl=%s", purchase.getId(), redirectUrl);
        }

        @Override
        public Status status(Payment payment) {
            return Status.valueOf(payment.getStatus());
        }
    };

    abstract String paymentUrl(@NotNull Purchase purchase, @URL String redirectUrl);

    public abstract Status status(Payment payment);
}

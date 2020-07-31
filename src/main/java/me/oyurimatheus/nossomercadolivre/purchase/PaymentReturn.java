package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.purchase.Payment.PaymentStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static me.oyurimatheus.nossomercadolivre.purchase.Payment.PaymentStatus.ERROR;
import static me.oyurimatheus.nossomercadolivre.purchase.Payment.PaymentStatus.SUCCESS;

/**
 * Specify the return of a payment gateway
 *
 * if the field names are different, maybe the annotation {@link com.fasterxml.jackson.annotation.JsonAlias} help
 */
class PaymentReturn {

    @NotNull
    @Min(1)
    private Long purchaseId;

    @NotBlank
    private String paymentId;

    @NotBlank
    private String status;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }

    public PaymentStatus payPalStatus() {
        if (status.equals("1")) {
            return SUCCESS;
        }

        return ERROR;
    }


    public PaymentStatus pagSeguroStatus() {
        if (status.equals("SUCESSO")) {
            return SUCCESS;
        }

        return ERROR;
    }
}

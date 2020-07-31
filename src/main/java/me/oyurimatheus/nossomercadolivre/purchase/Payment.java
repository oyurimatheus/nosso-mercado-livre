package me.oyurimatheus.nossomercadolivre.purchase;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static me.oyurimatheus.nossomercadolivre.purchase.Payment.PaymentStatus.SUCCESS;

@Embeddable
class Payment {

    private String paymentId;
    private PaymentStatus status;
    private LocalDateTime returnedAt;

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Payment() { }

    public Payment(String paymentId, PaymentStatus status) {
        this.paymentId = paymentId;
        this.status = status;
        this.returnedAt = now();
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public boolean isSuccessful() {
        return SUCCESS == status;
    }

    enum PaymentStatus {
        SUCCESS,
        ERROR
    }
}

package me.oyurimatheus.nossomercadolivre.purchase;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Embeddable
class Payment {

    private String paymentId;
    private String status;
    private LocalDateTime returnedAt;

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Payment() { }

    public Payment(String paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
        this.returnedAt = now();
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }
}

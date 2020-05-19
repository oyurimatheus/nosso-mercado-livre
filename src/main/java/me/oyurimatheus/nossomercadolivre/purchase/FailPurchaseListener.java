package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class FailPurchaseListener {

    private final EmailService emailService;

    FailPurchaseListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    void listen(PurchaseEvent event) {

        // if purchase is confirmed, this email must not be sent
        if (event.isConfirmed()) {
            return;
        }

        String body = "An error occurred when processing your payment, try again in this link: " + event.paymentUri();
        Email email = Email.to(event.buyerEmail())
                           .from(event.sellerEmail())
                           .subject("Payment could not be confirmed")
                           .body(body)
                           .product(event.getProduct())
                           .build();

        emailService.send(email);
    }
}

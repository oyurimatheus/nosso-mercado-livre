package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class SendPurchaseFailEmail implements PostPurchaseAction {

    private final EmailService emailService;

    SendPurchaseFailEmail(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * do the action if payment was not confirmed
     *
     * @param purchase the unconfirmed purchase
     * @param uriBuilder build uri component
     */
    @Override
    public void execute(Purchase purchase, UriComponentsBuilder uriBuilder) {
        if (purchase.isConfirmed()) {
            return;
        }

        var retryPaymentUrl = uriBuilder.path("/api/purchases/{id}")
                                        .buildAndExpand(purchase.getId())
                                        .toString();

        String body = "An error occurred when processing your payment, try again in this link: " + purchase.paymentUrl(retryPaymentUrl);
        Email email = Email.to(purchase.buyerEmail())
                .from(purchase.sellerEmail())
                .subject("Payment could not be confirmed")
                .body(body)
                .product(purchase.getProduct())
                .build();

        emailService.send(email);
    }
}

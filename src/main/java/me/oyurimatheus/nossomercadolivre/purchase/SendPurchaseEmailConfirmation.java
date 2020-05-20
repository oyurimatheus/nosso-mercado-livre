package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.products.Product;
import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.format.DateTimeFormatter;

@Component
class SendPurchaseEmailConfirmation implements PostPurchaseAction {

    private final EmailService emailService;

    SendPurchaseEmailConfirmation(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * do the action if purchase is confirmed
     *
     * @param postPaymentPurchase a success post payment purchase
     * @param uriBuilder build uri component
     */
    //TODO: Apply i18n in messages
    @Override
    public void execute(PostPaymentProcessedPurchase postPaymentPurchase, UriComponentsBuilder uriBuilder) {
        if (!postPaymentPurchase.isPaymentSuccessful()) {
            return;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm:ss");
        Product product = postPaymentPurchase.getProduct();

        String body = "Your " + postPaymentPurchase.getQuantity() + " product(s): " + product.getName() +
                      " is being prepared! Your purchase was confirmed at " + dateFormat.format(postPaymentPurchase.paymentConfirmedTime());

        Email email = Email.to(postPaymentPurchase.buyerEmail())
                .from(postPaymentPurchase.sellerEmail())
                .subject("Payment confirmed! Your product is being prepared")
                .body(body)
                .product(product)
                .build();

        emailService.send(email);
    }
}

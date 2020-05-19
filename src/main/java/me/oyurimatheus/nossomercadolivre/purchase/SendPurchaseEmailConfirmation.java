package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.products.Product;
import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailService;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
class SendPurchaseEmailConfirmation implements PostSuccessPurchaseAction {

    private final EmailService emailService;

    SendPurchaseEmailConfirmation(EmailService emailService) {
        this.emailService = emailService;
    }

    //TODO: Apply i18n in messages
    @Override
    public void execute(PurchaseEvent event) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm:ss");
        Product product = event.getProduct();

        String body = "Your " + event.getQuantityBought() + " product(s): " + product.getName() +
                      " is being prepared! Your purchase was confirmed at " + dateFormat.format(event.paymentConfirmedTime());

        Email email = Email.to(event.buyerEmail())
                .from(event.sellerEmail())
                .subject("Payment confirmed! Your product is being prepared")
                .body(body)
                .product(product)
                .build();

        emailService.send(email);
    }
}

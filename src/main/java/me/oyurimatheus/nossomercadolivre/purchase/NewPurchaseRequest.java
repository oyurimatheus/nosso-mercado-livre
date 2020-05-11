package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.products.Product;
import me.oyurimatheus.nossomercadolivre.users.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

class NewPurchaseRequest {

    private UUID productId;

    @Min(1)
    private int quantity;

    @NotNull
    private PaymentGateway paymentGateway;

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public Purchase toPurchase(User buyer, Product product) {

        return new Purchase(buyer, product, quantity, paymentGateway);
    }

}

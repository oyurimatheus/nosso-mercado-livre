package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.products.Product;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

class PurchaseEvent {

    private final Purchase purchase;
    private final UriComponentsBuilder uriBuilder;

    PurchaseEvent(Purchase purchase, UriComponentsBuilder uriBuilder) {

        this.purchase = purchase;
        this.uriBuilder = uriBuilder;
    }


    public boolean isConfirmed() {
        return purchase.isConfirmed();
    }

    public String buyerEmail() {
        return purchase.buyerEmail();
    }

    public String sellerEmail() {
        return purchase.sellerEmail();
    }


    public Product getProduct() {
        return purchase.getProduct();
    }

    public String paymentUri() {
        var redirectUrl = uriBuilder.path("/api/purchases/{id}")
                                    .buildAndExpand(purchase.getId())
                                    .toString();

        return purchase.paymentUrl(redirectUrl);
    }

    public LocalDateTime paymentConfirmedTime() {
        return purchase.paymentConfirmedTime();
    }

    public int getQuantityBought() {
        return purchase.getQuantity();
    }

    public Long purchaseId() {
        return purchase.getId();
    }
}

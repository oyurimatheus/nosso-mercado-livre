package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.purchase.SellersRankingClient.SellersRankingRequest;
import org.springframework.stereotype.Component;

@Component
class SendPurchaseConfirmationToSellersSystem implements PostSuccessPurchaseAction {

    private final SellersRankingClient sellersRankingClient;

    SendPurchaseConfirmationToSellersSystem(SellersRankingClient sellersRankingClient) {
        this.sellersRankingClient = sellersRankingClient;
    }

    @Override
    public void execute(PurchaseEvent event) {

        SellersRankingRequest request = new SellersRankingRequest(event.purchaseId(), event.sellerEmail());
        sellersRankingClient.requestInvoice(request);
    }
}

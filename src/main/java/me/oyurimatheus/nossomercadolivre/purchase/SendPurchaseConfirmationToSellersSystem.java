package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.purchase.SellersRankingClient.SellersRankingRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class SendPurchaseConfirmationToSellersSystem implements PostPurchaseAction {

    private final SellersRankingClient sellersRankingClient;

    SendPurchaseConfirmationToSellersSystem(SellersRankingClient sellersRankingClient) {
        this.sellersRankingClient = sellersRankingClient;
    }

    /**
     * do the action if purchase is confirmed
     *
     * @param purchase the success purchase
     * @param uriBuilder build uri component
     */
    @Override
    public void execute(Purchase purchase, UriComponentsBuilder uriBuilder) {
        if (!purchase.isConfirmed()) {
            return;
        }

        SellersRankingRequest request = new SellersRankingRequest(purchase.getId(), purchase.sellerEmail());
        sellersRankingClient.requestInvoice(request);
    }
}

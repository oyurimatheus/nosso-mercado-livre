package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.purchase.InvoiceClient.InvoiceRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class SendPurchaseConfirmationToInvoiceSystem implements PostPurchaseAction {

    private final InvoiceClient invoiceClient;

    SendPurchaseConfirmationToInvoiceSystem(InvoiceClient invoiceClient) {
        this.invoiceClient = invoiceClient;
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

        InvoiceRequest request = new InvoiceRequest(purchase.getId(), purchase.buyerEmail());
        invoiceClient.requestInvoice(request);
    }
}

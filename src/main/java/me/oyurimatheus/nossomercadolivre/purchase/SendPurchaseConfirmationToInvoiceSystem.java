package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.purchase.InvoiceClient.InvoiceRequest;
import org.springframework.stereotype.Component;

@Component
class SendPurchaseConfirmationToInvoiceSystem implements PostSuccessPurchaseAction {

    private final InvoiceClient invoiceClient;

    SendPurchaseConfirmationToInvoiceSystem(InvoiceClient invoiceClient) {
        this.invoiceClient = invoiceClient;
    }

    @Override
    public void execute(PurchaseEvent event) {
        InvoiceRequest request = new InvoiceRequest(event.purchaseId(), event.buyerEmail());
        invoiceClient.requestInvoice(request);
    }
}

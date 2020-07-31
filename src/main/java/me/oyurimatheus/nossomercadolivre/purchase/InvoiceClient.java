package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(url = "${invoice.url}", name = "invoiceSystem")
interface InvoiceClient {

    @RequestMapping(method = POST, value = "/register/", produces = "application/json")
    void requestInvoice(InvoiceRequest request);

    class InvoiceRequest {

        private Long purchaseId;
        private String buyerId;

        /**
         * @deprecated frameworks eyes only
         */
        @Deprecated
        InvoiceRequest() { }

        public InvoiceRequest(Long purchaseId, String buyerId) {
            this.purchaseId = purchaseId;
            this.buyerId = buyerId;
        }

        public Long getPurchaseId() {
            return purchaseId;
        }

        public String getBuyerId() {
            return buyerId;
        }
    }
}



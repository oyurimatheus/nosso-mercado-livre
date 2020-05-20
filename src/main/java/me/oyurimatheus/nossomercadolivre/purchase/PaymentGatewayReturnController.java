package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/purchases/confirm-payment")
class PaymentGatewayReturnController {


    private final PurchaseRepository purchaseRepository;
    private final Set<PostPurchaseAction> postPurchaseActions;

    PaymentGatewayReturnController(PurchaseRepository purchaseRepository,
                                   Set<PostPurchaseAction> postPurchaseActions) {
        this.purchaseRepository = purchaseRepository;
        this.postPurchaseActions = postPurchaseActions;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> confirmPayment(@RequestBody @Valid PaymentReturn paymentReturn,
                                            UriComponentsBuilder uriBuilder) throws BindException {

        Optional<Purchase> possiblePurchase = purchaseRepository.findById(paymentReturn.getPurchaseId());
        if (possiblePurchase.isEmpty()) {
            var bindException = new BindException(new Object(), "");
            bindException.reject("purchase.id.doesNotExist", "Purchase id does not exist");

            throw bindException;
        }

        var purchase = possiblePurchase.get();
        Payment payment = paymentReturn.toPayment();
        purchase.process(payment);

        postPurchaseActions.forEach(action -> action.execute(purchase, uriBuilder));

        return ok().build();
    }
}

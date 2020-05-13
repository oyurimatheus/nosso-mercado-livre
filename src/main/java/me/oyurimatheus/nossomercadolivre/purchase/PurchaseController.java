package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.products.ProductRepository;
import me.oyurimatheus.nossomercadolivre.shared.validators.ObjectIsRegisteredValidator;
import me.oyurimatheus.nossomercadolivre.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/purchase")
class PurchaseController {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    PurchaseController(ProductRepository productRepository,
                       PurchaseRepository purchaseRepository) {
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @PostMapping
    public ResponseEntity<?> buy(@RequestBody @Valid NewPurchaseRequest newPurchase,
                                 @AuthenticationPrincipal User buyer,
                                 UriComponentsBuilder uriBuilder) {

        var product = productRepository.findById(newPurchase.getProductId()).get();

        Optional<Purchase> possiblePurchase = product.reserveQuantityFor(newPurchase, buyer);

        if (possiblePurchase.isEmpty()) {
            var response = new HashMap<>();
            response.put("error", "Product is out of stock");

            return badRequest().body(response);
        }

        Purchase purchase = possiblePurchase.get();
        productRepository.save(product);
        purchaseRepository.save(purchase);

        var redirectUrl = uriBuilder.path("/api/purchases/{id}/confirm-payment")
                                    .buildAndExpand(purchase.getId())
                                    .toString();

        String paymentUrl = purchase.paymentUrl(redirectUrl);


        var response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);

        return ok(response);
    }

    @InitBinder(value = { "newPurchaseRequest" })
    void initBinder(WebDataBinder binder) {

        binder.addValidators(
                new ObjectIsRegisteredValidator<>("productId",
                        "product.id.dontExist",
                        NewPurchaseRequest.class,
                        productRepository::existsById));
    }
}

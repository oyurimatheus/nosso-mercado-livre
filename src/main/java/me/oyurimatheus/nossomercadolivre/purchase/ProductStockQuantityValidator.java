package me.oyurimatheus.nossomercadolivre.purchase;

import me.oyurimatheus.nossomercadolivre.products.Product;
import me.oyurimatheus.nossomercadolivre.products.ProductRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

class ProductStockQuantityValidator implements Validator {

    private final ProductRepository productRepository;

    ProductStockQuantityValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewPurchaseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var newPurchase = (NewPurchaseRequest) target;
        Product product  = productRepository.findById(newPurchase.getProductId()).get();

        if (product.getStockQuantity() < newPurchase.getQuantity()) {
            errors.rejectValue("quantity", "newPurchase.quantity.outOfStock", "You requested a quantity greater than stock");
        }
    }
}

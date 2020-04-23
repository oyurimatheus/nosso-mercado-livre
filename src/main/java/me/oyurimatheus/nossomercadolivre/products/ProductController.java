package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.CategoryRepository;
import me.oyurimatheus.nossomercadolivre.configuration.security.TokenManager;
import me.oyurimatheus.nossomercadolivre.shared.ObjectIsRegisteredValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoUploader photoUploader;
    private final TokenManager tokenManager;

    public ProductController(ProductRepository productRepository,
                             CategoryRepository categoryRepository,
                             PhotoUploader photoUploader,
                             TokenManager tokenManager) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.photoUploader = photoUploader;
        this.tokenManager = tokenManager;
    }

    @PostMapping
    ResponseEntity<?> create(@RequestHeader(name = "Authorization") String header,
                             @RequestBody @Valid NewProductRequest newProduct) {
        String token = header.replace("Bearer ", "");
        Long userId = tokenManager.getUserIdFromToken(token);

        Product product = newProduct.toProduct(photoUploader, categoryRepository::findCategoryById, userId);
        productRepository.save(product);

        URI location = URI.create("/api/products/" + product.getId());
        return created(location).build();
    }

    @InitBinder(value = { "newProductRequest" })
    void initBinder(WebDataBinder binder) {

        binder.addValidators(
                new ObjectIsRegisteredValidator<>("categoryId",
                        "category.id.dontExist",
                        NewProductRequest.class,
                        categoryRepository::existsById));
    }
}

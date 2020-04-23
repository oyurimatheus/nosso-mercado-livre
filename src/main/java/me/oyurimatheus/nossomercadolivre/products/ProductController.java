package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.CategoryRepository;
import me.oyurimatheus.nossomercadolivre.configuration.security.TokenManager;
import me.oyurimatheus.nossomercadolivre.shared.ObjectIsRegisteredValidator;
import me.oyurimatheus.nossomercadolivre.users.User;
import me.oyurimatheus.nossomercadolivre.users.UserRepository;
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
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoUploader photoUploader;
    private final TokenManager tokenManager;

    public ProductController(ProductRepository productRepository,
                             UserRepository userRepository,
                             CategoryRepository categoryRepository,
                             PhotoUploader photoUploader,
                             TokenManager tokenManager) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.photoUploader = photoUploader;
        this.tokenManager = tokenManager;
    }

    @PostMapping
    ResponseEntity<?> create(@RequestHeader(name = "Authorization") String header,
                             @RequestBody @Valid NewProductRequest newProduct) {
        String token = header.replace("Bearer ", "");
        Long userId = tokenManager.getUserIdFromToken(token);

        User user = userRepository.findById(userId).get();

        Product product = newProduct.toProduct(photoUploader, categoryRepository::findCategoryById, user);
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

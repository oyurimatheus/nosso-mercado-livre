package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.CategoryRepository;
import me.oyurimatheus.nossomercadolivre.shared.ObjectIsRegisteredValidator;
import me.oyurimatheus.nossomercadolivre.users.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher publisher;

    public ProductController(CategoryRepository categoryRepository,
                             ApplicationEventPublisher publisher) {
        this.categoryRepository = categoryRepository;
        this.publisher = publisher;
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid NewProductRequest newProduct,
                             @AuthenticationPrincipal User user) {

        PreProduct product = newProduct.toPreProduct(categoryRepository::findCategoryById, user);

        publisher.publishEvent(new NewProductEvent(product, newProduct.getPhotos()));

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

package me.oyurimatheus.nossomercadolivre.categories;

import me.oyurimatheus.nossomercadolivre.shared.validators.UniqueFieldValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/categories")
class CategoryController {

    private final CategoryRepository categoryRepository;

    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    ResponseEntity<?> createCategory(@RequestBody @Valid NewCategoryRequest newCategory) {
        Category category = newCategory.toCategory(categoryRepository::findCategoryById);

        categoryRepository.save(category);

        URI location = URI.create("/api/categories/" + category.getId());
        return ResponseEntity.created(location).build();
     }

    @InitBinder(value = { "newCategoryRequest" })
    void initBinder(WebDataBinder binder) {


        binder.addValidators(new UniqueFieldValidator<>("name",
                                                       "category.name.alreadyExists",
                                                        NewCategoryRequest.class,
                                                        categoryRepository::existsByName),
                             new SuperCategoryExistsValidator(categoryRepository));
    }
}

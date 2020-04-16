package me.oyurimatheus.nossomercadolivre.categories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/categories")
class CategoryController {

    private final CategoryRepository categoryRepository;
    private final NewCategoryRequestToCategoryConverter newCategoryRequestToCategory;

    CategoryController(CategoryRepository categoryRepository,
                       NewCategoryRequestToCategoryConverter newCategoryRequestToCategory) {
        this.categoryRepository = categoryRepository;
        this.newCategoryRequestToCategory = newCategoryRequestToCategory;
    }

    @PostMapping
    ResponseEntity<?> createCategory(@RequestBody @Valid NewCategoryRequest newCategory) {
        Category category = newCategoryRequestToCategory.convert(newCategory);

        categoryRepository.save(category);

        URI location = URI.create("/api/categories/" + category.getId());
        return ResponseEntity.created(location).build();
     }

    @InitBinder(value = { "newCategoryRequest" })
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new CategoryUniqueNameValidator(categoryRepository),
                             new SuperCategoryExistsValidator(categoryRepository));
    }
}

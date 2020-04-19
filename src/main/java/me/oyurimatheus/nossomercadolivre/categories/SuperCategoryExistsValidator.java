package me.oyurimatheus.nossomercadolivre.categories;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

class SuperCategoryExistsValidator implements Validator {

    private final CategoryRepository categoryRepository;

    SuperCategoryExistsValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewCategoryRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var newCategory = (NewCategoryRequest) target;
        Optional<Long> superCategory = newCategory.getSuperCategory();

        if (superCategory.isPresent()) {
            Long superCategoryId = superCategory.get();

            if (!categoryRepository.existsById(superCategoryId)) {
                errors.rejectValue("superCategory", "category.superCategory", "The super category does not exists");
            }
        }

    }
}

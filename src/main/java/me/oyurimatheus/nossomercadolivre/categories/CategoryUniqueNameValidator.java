package me.oyurimatheus.nossomercadolivre.categories;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class CategoryUniqueNameValidator implements Validator {

    private final CategoryRepository categoryRepository;

    CategoryUniqueNameValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewCategoryRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var newCategory = (NewCategoryRequest) target;
        String name = newCategory.getName();

        if (categoryRepository.existsByName(name)) {
            errors.rejectValue("name", "category.name.alreadyExists", "this category is already registered");
        }
    }
}

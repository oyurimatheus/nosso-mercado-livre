package me.oyurimatheus.nossomercadolivre.categories;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * This is a class used to convert a NewCategoryRequest to a Category domain class
 */
@Component
class NewCategoryRequestToCategoryConverter {

    private final CategoryRepository categoryRepository;

    NewCategoryRequestToCategoryConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     *
     * @throws CategoryDontExistException if the super category id does not exists in database
     *
     * @param newCategory the new category candidate
     * @return a Category domain object
     */
    Category convert(@NotNull NewCategoryRequest newCategory) {
        requireNonNull(newCategory, "newCategory must not be null");

        String name = newCategory.getName();
        Optional<Long> possibleSuperCategory = newCategory.getSuperCategory();

        if (possibleSuperCategory.isPresent()) {
            Long superCategoryId = possibleSuperCategory.get();
            Category superCategory = categoryRepository.findCategoryById(superCategoryId)
                                                       .orElseThrow(() -> new CategoryDontExistException(format("The category %s informed does not exists", superCategoryId)));

            return new Category(name, superCategory);
        }

        return new Category(name);
    }
}

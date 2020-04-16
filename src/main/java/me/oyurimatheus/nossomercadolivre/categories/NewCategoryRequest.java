package me.oyurimatheus.nossomercadolivre.categories;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;
import java.util.StringJoiner;

class NewCategoryRequest {

    @NotEmpty
    private String name;

    @Min(value = 1)
    private Long superCategory;

    public String getName() {
        return name;
    }

    public void setName(@NotEmpty String name) {
        this.name = name;
    }

    public Optional<Long> getSuperCategory() {
        return Optional.ofNullable(superCategory);
    }

    public void setSuperCategory(@Min(value = 1) Long superCategory) {
        this.superCategory = superCategory;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NewCategoryRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("superCategory=" + superCategory)
                .toString();
    }
}

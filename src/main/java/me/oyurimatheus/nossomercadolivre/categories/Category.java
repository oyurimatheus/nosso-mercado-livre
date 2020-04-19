package me.oyurimatheus.nossomercadolivre.categories;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

@Table(name = "categories")
@Entity
class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotEmpty
    @Column(name = "category_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "super_category_id")
    private Category superCategory;

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private Category() { }

    /**
     * if this category does not have a super category @see #Category(String name)
     *
     * @param name the category name
     * @param superCategory the category super category
     */
    Category(@NotEmpty String name, @NotNull Category superCategory) {
        hasText(name, "name must not be empty");
        notNull(superCategory, "superCategory must not be null using this constructor");

        this.name = name;
        this.superCategory = superCategory;
    }

    /**
     * if this category has a super category @see #Category(String name, Category superCategory)
     *
     * @param name the category name
     */
    Category(@NotEmpty String name) {
        hasText(name, "name must not be empty");

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("superCategory=" + superCategory)
                .toString();
    }
}

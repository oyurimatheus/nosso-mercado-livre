package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.users.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;
import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.util.Assert.hasText;

@Table(name = "products_opinion")
@Entity
class ProductOpinion {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Range(min = 1, max = 5)
    private Integer rating;

    @NotBlank
    private String title;

    @Length(max = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    /**
     * @deprecated  framework eyes only
     */
    @Deprecated
    private ProductOpinion() { }

    ProductOpinion(@Range(min = 1, max = 5) Integer rating,
                   @NotBlank String title,
                   @Length(max = 500) String description,
                   @NotNull Product product,
                   @NotNull User user) {


        ratingInRange(rating);
        hasText(title, "title must not be blank");
        descriptionFitsMaxSize(description);
        requireNonNull(product, "product must not be null");
        requireNonNull(user, "user must not be null");

        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    private void descriptionFitsMaxSize(@Length(max = 500) String description) {
        if (description.length() > 500) {
            throw new IllegalArgumentException("description must have at most 500 characters");
        }
    }

    private void ratingInRange(@Range(min = 1, max = 5) Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 1 and 5");
        }
    }

    public Long getId() {
        return id;
    }
}

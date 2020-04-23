package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.Category;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notEmpty;

@Table(name = "products")
@Entity
class Product {

    @Id
    @Column(name = "product_id")
    private UUID id;

    @NotBlank
    @Column(name = "product_name")
    private String name;

    @DecimalMin(value = "0.01")
    @Column(name = "product_price")
    private BigDecimal price;

    @Min(value = 0)
    @Column(name = "product_stock_quantity")
    private Integer stockQuantity;

    @ElementCollection
    @CollectionTable(name = "product_photos",
            joinColumns = @JoinColumn(name = "product_id"))
    @Size(min = 1)
    private List<Photo> photos;

    @ElementCollection
    @CollectionTable(name = "product_characteristcs",
            joinColumns = @JoinColumn(name = "product_id"))
    @Size(min = 3)
    private List<Characteristic> characteristics;

    @Lob
    @Length(max = 1000)
    @Column(name = "product_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @PastOrPresent
    @CreationTimestamp
    @Column(name = "product_created_at")
    private LocalDateTime createdAt = now();


    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private Product() { }

    Product(@NotNull UUID id,
            @NotBlank String name,
            @DecimalMin(value = "0.01") BigDecimal price,
            @Min(value = 0) Integer stockQuantity,
            @Size(min = 1) List<Photo> photos,
            @Size(min = 3) List<Characteristic> characteristics,
            @Length(max = 1000) @NotBlank String description,
            @NotNull Category category) {

        requireNonNull(id, "id must not be null");
        hasText(name, "product must have a name");
        greaterThanZero(price, "price must be greater than 0");
        greaterOrEqualToZero(stockQuantity, "stockQuantity must be greater than 0");
        notEmpty(photos, "product must have at least one photo");
        atLeastThree(characteristics, "product must have at least three characteristics");
        hasText(description, "description must not be blank");
        requireNonNull(category, "category must not be null");

        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.photos =  photos;
        this.characteristics = characteristics;
        this.description = description;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    private void atLeastThree(List<Characteristic> characteristics, String msg) {
        if (characteristics != null && characteristics.size() < 3) {
            throw new IllegalArgumentException(msg);
        }
    }

    private void greaterOrEqualToZero(Integer stockQuantity, String msg) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    private void greaterThanZero(BigDecimal price, String msg) {
        if (price.compareTo(new BigDecimal("0.01")) < 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("stockQuantity=" + stockQuantity)
                .add("photos=" + photos)
                .add("caracteristics=" + characteristics)
                .add("description='" + description + "'")
                .add("category=" + category)
                .add("createdAt=" + createdAt)
                .toString();
    }
}
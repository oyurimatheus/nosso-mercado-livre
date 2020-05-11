package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.Category;
import me.oyurimatheus.nossomercadolivre.purchase.Purchase;
import me.oyurimatheus.nossomercadolivre.users.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.averagingDouble;
import static org.springframework.util.Assert.notEmpty;

@Table(name = "products")
@Entity
public
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
    private Set<Characteristic> characteristics;

    @Lob
    @Length(max = 1000)
    @Column(name = "product_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "product")
    private List<ProductOpinion> opinions;

    @OneToMany(mappedBy = "product")
    private List<Question> questions;

    @Version
    @Column(name = "product_created_at")
    private LocalDateTime createdAt = now();


    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private Product() { }

    Product(@NotNull PreProduct preProduct,
            @Size(min = 1) List<Photo> photos,
            @Size(min = 3) Set<Characteristic> characteristics) {

        requireNonNull(preProduct, "preProduct must not be null");
        notEmpty(photos, "product must have at least one photo");
        atLeastThree(characteristics, "product must have at least three characteristics");

        this.id = preProduct.getId();
        this.name = preProduct.getName();
        this.price = preProduct.getPrice();
        this.stockQuantity = preProduct.getStockQuantity();
        this.photos = photos;
        this.characteristics = characteristics;
        this.description = preProduct.getDescription();
        this.category = preProduct.getCategory();
        this.user = preProduct.getUser();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public List<ProductOpinion> getOpinions() {
        return opinions;
    }

    /**
     * @return questions ordered by the newest asked question
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     *
     * @return a list of categories from category mother to product's category
     */
    public List<Category> getCategoriesHierarchy() {
        return category.getCategoryHierarchy();
    }

    public String sellerEmail() {
        return user.getUsername();
    }

    public Set<Product> sellerOtherProducts() {
        Set<Product> products = new HashSet<>(user.getProducts());
        products.remove(this);

        return products;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal rating() {
        double rating = opinions.stream()
                .collect(averagingDouble(ProductOpinion::getRating));

        return BigDecimal.valueOf(rating);
    }

    private void atLeastThree(Set<Characteristic> characteristics, String msg) {
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

    /**
     * changes the product {@link #stockQuantity}
     *
     * @param purchase a new Purchase
     */
    public void reserveQuantityFor(Purchase purchase) {
        if (stockQuantity < purchase.getQuantity()) {
            throw new IllegalStateException("Quantity required is unavailable");
        }

        stockQuantity -= purchase.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(user, product.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, user);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("stockQuantity=" + stockQuantity)
                .add("photos=" + photos)
                .add("characteristics=" + characteristics)
                .add("description='" + description + "'")
                .add("category=" + category)
                .add("user=" + user)
                .add("createdAt=" + createdAt)
                .toString();
    }
}

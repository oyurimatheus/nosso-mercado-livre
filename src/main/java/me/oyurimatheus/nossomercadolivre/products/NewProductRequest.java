package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.Category;
import org.hibernate.validator.constraints.Length;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

class NewProductRequest {

    @NotBlank
    private String name;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Min(0)
    private Integer stockQuantity;

    /**
     * Photos list in base 64
     */
    @Size(min = 1)
    private List<String> photos;

    @Size(min = 3)
    private List<NewCharacteristicRequest> characteristics;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Long categoryId;

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private NewProductRequest() { }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public List<NewCharacteristicRequest> getCharacteristics() {
        return characteristics;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product toProduct(PhotoUploader photoUploader, Function<Long, Optional<Category>> findCategoryById, Long userId) {

        UUID productId = UUID.randomUUID();

        List<Photo> photos = photoUploader.upload(this.photos, userId, productId);

        List<Characteristic> characteristics = this.characteristics.stream()
                                                                   .map(NewCharacteristicRequest::toCharacteristic)
                                                                   .collect(toList());

        Category category = findCategoryById.apply(categoryId)
                                            .orElseThrow(() -> new IllegalStateException(format("Category %s is not registered", categoryId)));

        return new Product(productId, name, price, stockQuantity, photos, characteristics, description, category);
    }
}

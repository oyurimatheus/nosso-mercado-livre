package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.Category;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

class ProductDetailsResponse {

    private UUID id;
    private BigDecimal price;
    private Integer stockQuantity;
    private List<CharacteristicResponse> characteristics;
    private List<String> photos;
    private String description;
    private List<String> categoryHierarchy;
    private String sellersDetails;
    private BigDecimal rating;
    private List<ProductOpinionResponse> opinions;
    private List<QuestionResponse> questions;

    /**
     * @deprecated framework eyes only
     */
    @Deprecated
    private ProductDetailsResponse() { }

    ProductDetailsResponse(Product product, UriComponentsBuilder uriBuilder) {
        this.id = product.getId();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.categoryHierarchy = makeCategoryHierarchy(product.getCategory());

        this.sellersDetails = uriBuilder.path("/api/products/{email}")
                                        .buildAndExpand(product.sellerEmail())
                                        .toString();

        this.characteristics = CharacteristicResponse.from(product.getCharacteristics());

        this.photos = product.getPhotos()
                             .stream()
                             .map(Photo::getUrl)
                             .collect(toList());

        this.rating = product.rating();
        this.opinions = ProductOpinionResponse.from(product.getOpinions());

        this.questions = QuestionResponse.from(product.getQuestionsFromNewest());

    }

    private List<String> makeCategoryHierarchy(Category category) {
        LinkedList<String> categoriesOrder = new LinkedList<>();
        categoriesOrder.addFirst(category.getName());

        while (category.hasSuperCategory()) {
            category = category.getSuperCategory();
            categoriesOrder.addFirst(category.getName());
        }

        return categoriesOrder;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public List<CharacteristicResponse> getCharacteristics() {
        return characteristics;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategoryHierarchy() {
        return categoryHierarchy;
    }

    public String getSellersDetails() {
        return sellersDetails;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public List<ProductOpinionResponse> getOpinions() {
        return opinions;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }
}

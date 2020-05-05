package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.categories.Category;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

class ProductDetailsResponse {

    private UUID id;
    private BigDecimal price;
    private Integer stockQuantity;
    private List<CharacteristicResponse> characteristics;
    private List<String> photos;
    private List<SimpleProductDetailsResponse> sellerOtherProducts;
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

    ProductDetailsResponse(Product product, List<Product> sellerOtherProducts, UriComponentsBuilder uriBuilder) {
        this.id = product.getId();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.categoryHierarchy = product.getCategoriesHierarchy()
                                        .stream()
                                        .map(Category::getName)
                                        .collect(toUnmodifiableList());

        this.sellersDetails = uriBuilder.path("/api/products/{email}")
                                        .buildAndExpand(product.sellerEmail())
                                        .toString();

        this.characteristics = CharacteristicResponse.from(product.getCharacteristics());

        this.photos = product.getPhotos()
                             .stream()
                             .map(Photo::getUrl)
                             .collect(toList());

        sellerOtherProducts.remove(product);
        this.sellerOtherProducts = makeSellerOtherProductResponse(sellerOtherProducts);
        this.rating = product.rating();
        this.opinions = ProductOpinionResponse.from(product.getOpinions());

        List<Question> questions = sortQuestionByNewest(product.getQuestions());
        this.questions = QuestionResponse.from(questions);

    }

    private List<SimpleProductDetailsResponse> makeSellerOtherProductResponse(List<Product> sellerOtherProducts) {
        return sellerOtherProducts.stream()
                                  .map(SimpleProductDetailsResponse::new)
                                  .collect(toUnmodifiableList());
    }

    private List<Question> sortQuestionByNewest(List<Question> questions) {
        return questions.stream()
                .sorted((q1, q2) -> q2.getCreatedAt().compareTo(q1.getCreatedAt()))
                .collect(toUnmodifiableList());
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

    public List<SimpleProductDetailsResponse> getSellerOtherProducts() {
        return sellerOtherProducts;
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

    /**
     * Represents a product with its basic information
     */
    private static class SimpleProductDetailsResponse {

        private UUID id;
        private Photo photo;
        private String name;
        private BigDecimal price;

        /**
         * @deprecated framework eyes only
         */
        @Deprecated
        private SimpleProductDetailsResponse() { }

        private SimpleProductDetailsResponse(Product product) {
            this.id = product.getId();
            this.photo = product.getPhotos().get(0);
            this.name = product.getName();
            this.price = product.getPrice();
        }

        public UUID getId() {
            return id;
        }

        public Photo getPhoto() {
            return photo;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}

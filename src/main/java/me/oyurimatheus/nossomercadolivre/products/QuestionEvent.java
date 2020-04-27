package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.users.User;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.StringJoiner;

/**
 * This class represents an event that is propagated when someone create a {@link Question}
 */
class QuestionEvent {

    private final String title;
    private final String sellersEmail;
    private final String possibleBuyer;
    private final String productUri;

    /**
     *
     * @param question that was created to a specific product
     * @param uriBuilder the url creator
     */
    public QuestionEvent(Question question, UriComponentsBuilder uriBuilder) {
        this.title = question.getTitle();

        Product product = question.getProduct();
        this.sellersEmail = product.sellerEmail();

        User user = question.getUser();
        this.possibleBuyer = user.getUsername();

        this.productUri = uriBuilder.path("/api/products/{id}")
                                    .buildAndExpand(product.getId())
                                    .toString();
    }

    public String getTitle() {
        return title;
    }

    public String getSellersEmail() {
        return sellersEmail;
    }

    public String getPossibleBuyer() {
        return possibleBuyer;
    }

    public String getProductUri() {
        return productUri;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", QuestionEvent.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("sellersEmail='" + sellersEmail + "'")
                .add("possibleBuyer='" + possibleBuyer + "'")
                .add("productUri=" + productUri)
                .toString();
    }
}

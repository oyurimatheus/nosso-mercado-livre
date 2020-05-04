package me.oyurimatheus.nossomercadolivre.products;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class ProductOpinionResponse {


    private String title;
    private String description;
    private Integer rating;

    /**
     * @deprecated framework eyes only
     */
    @Deprecated
    private ProductOpinionResponse() { }

    private ProductOpinionResponse(ProductOpinion opinion) {
        this.title = opinion.getTitle();
        this.description = opinion.getDescription();
        this.rating = opinion.getRating();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRating() {
        return rating;
    }

    public static List<ProductOpinionResponse> from(List<ProductOpinion> opinions) {

        return opinions.stream()
                       .map(ProductOpinionResponse::new)
                       .collect(toList());
    }
}

package me.oyurimatheus.nossomercadolivre.products;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import static org.springframework.util.Assert.hasText;

@Embeddable
class Photo {

    @URL
    @NotBlank
    private String url;

    private Photo() { }

    /**
     * @param url where photo is stored
     */
    Photo(String url) {
        hasText(url, "Photo must have an url");
        this.url = url;
    }
}

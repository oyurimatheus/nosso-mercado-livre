package me.oyurimatheus.nossomercadolivre.products;

import java.util.List;

class NewProductEvent {

    private final PreProduct preProduct;
    private final List<String> photos;

    NewProductEvent(PreProduct preProduct, List<String> photos) {
        this.preProduct = preProduct;
        this.photos = photos;
    }

    public PreProduct getPreProduct() {
        return preProduct;
    }

    public List<String> getPhotos() {
        return photos;
    }
}

package me.oyurimatheus.nossomercadolivre.products;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class PreProductListener {

    private final PhotoUploader photoUploader;
    private final ProductRepository productRepository;

    PreProductListener(PhotoUploader photoUploader,
                       ProductRepository productRepository) {
        this.photoUploader = photoUploader;
        this.productRepository = productRepository;
    }

    @EventListener
    void listen(NewProductEvent newProductEvent) {
        var preProduct = newProductEvent.getPreProduct();

        var photos = photoUploader.upload(newProductEvent.getPhotos(), preProduct);

        Product product = new Product(preProduct, photos);
        productRepository.save(product);
    }
}

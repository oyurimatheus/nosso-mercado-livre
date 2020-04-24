package me.oyurimatheus.nossomercadolivre.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Component
class LocalPhotoUploader implements PhotoUploader {

    private static final Logger LOG = LoggerFactory.getLogger(LocalPhotoUploader.class);

    /**
     * Url is composed by [userID]/[productId][timestamp]
     */
    private static final String PRODUCTS_BUCKET_URL = "https://s3.nosso-mercado-livre.amazon/products/%s/%s/%s";

    /**
     *
     * @param photos photos in base64 to upload
     * @param preProduct a previous state of a product
     * @return a list of {@link Photo} with images url
     */
    @Override
    public List<Photo> upload(List<String> photos, PreProduct preProduct) {

        return photos.stream()
                     .map(photo -> storagePhoto(photo, preProduct))
                     .collect(toList());

    }

    /**
     *
     * @param photo photo in base64
     * @param preProduct a previous state of a product whose photos belong
     * @return a {@link Photo} with its url
     */
    private Photo storagePhoto(String photo, PreProduct preProduct) {
        String photoName = now().toString();
        String url = String.format(PRODUCTS_BUCKET_URL, preProduct.getUser().getId(), preProduct.getId(), photoName);

        LOG.info("[PRODUCT] [PHOTO] Uploaded product photo to: {}", url);

        return new Photo(url);
    }
}

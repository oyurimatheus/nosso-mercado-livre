package me.oyurimatheus.nossomercadolivre.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Component
class PhotoUploader {

    private static final Logger LOG = LoggerFactory.getLogger(PhotoUploader.class);

    /**
     * Url is composed by [userID]/[productId][timestamp]
     */
    private static final String PRODUCTS_BUCKET_URL = "https://s3.nosso-mercado-livre.amazon/products/%s/%s/%s";

    /**
     *
     * @param photos photos in base64 to upload
     * @param userId user's who posted the product
     * @param productId product's id
     * @return a list of {@link Photo} with images url
     */
    public List<Photo> upload(List<String> photos, Long userId, UUID productId) {

        return photos.stream()
                     .map(photo -> storagePhoto(photo, userId, productId))
                     .collect(toList());

    }

    /**
     *
     * @param photo photo in base64
     * @param userId user's who is creating the product
     * @param productId product's id
     * @return a {@link Photo} with its url
     */
    private Photo storagePhoto(String photo, Long userId, UUID productId) {
        String photoName = now().toString();
        String url = String.format(PRODUCTS_BUCKET_URL, userId, productId, photoName);

        LOG.info("[PRODUCT] [PHOTO] Uploaded product photo to: {}", url);

        return new Photo(url);
    }
}

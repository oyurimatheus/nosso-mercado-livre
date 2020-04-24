package me.oyurimatheus.nossomercadolivre.products;

import java.util.List;

public interface PhotoUploader {

    List<Photo> upload(List<String> photos, PreProduct preProduct);
}

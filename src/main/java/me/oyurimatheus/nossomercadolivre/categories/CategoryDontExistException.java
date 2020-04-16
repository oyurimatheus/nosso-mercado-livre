package me.oyurimatheus.nossomercadolivre.categories;

class CategoryDontExistException extends RuntimeException {

    CategoryDontExistException(String msg) {
        super(msg);
    }
}

package me.oyurimatheus.nossomercadolivre.products;

import org.springframework.data.repository.Repository;

interface ProductOpinionRepository extends Repository<ProductOpinion, Long> {

    ProductOpinion save(ProductOpinion productOpinion);
}

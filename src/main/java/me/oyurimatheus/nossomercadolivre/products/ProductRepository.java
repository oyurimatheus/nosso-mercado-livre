package me.oyurimatheus.nossomercadolivre.products;

import org.springframework.data.repository.Repository;

import java.util.UUID;

interface ProductRepository extends Repository<Product, UUID> {

    Product save(Product product);
}

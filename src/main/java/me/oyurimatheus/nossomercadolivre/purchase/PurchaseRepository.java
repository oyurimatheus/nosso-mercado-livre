package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface PurchaseRepository extends Repository<Purchase, Long> {

    Purchase save(Purchase purchase);

    Optional<Purchase> findById(Long id);
}

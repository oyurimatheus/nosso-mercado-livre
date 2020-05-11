package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.data.repository.Repository;

interface PurchaseRepository extends Repository<Purchase, Long> {

    Purchase save(Purchase purchase);
}

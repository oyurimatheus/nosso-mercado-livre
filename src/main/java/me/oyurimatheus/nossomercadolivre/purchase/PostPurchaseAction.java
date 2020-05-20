package me.oyurimatheus.nossomercadolivre.purchase;

import org.springframework.web.util.UriComponentsBuilder;

interface PostPurchaseAction {

    void execute(Purchase event, UriComponentsBuilder uriBuilder);
}

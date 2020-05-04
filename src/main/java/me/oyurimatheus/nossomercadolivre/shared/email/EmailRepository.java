package me.oyurimatheus.nossomercadolivre.shared.email;

import org.springframework.data.repository.Repository;

public interface EmailRepository extends Repository<Email, Long> {

    Email save(Email email);
}

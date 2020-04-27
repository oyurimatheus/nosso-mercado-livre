package me.oyurimatheus.nossomercadolivre.shared.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class LocalEmailService implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalEmailService.class);

    @Override
    public void send(Email email) {

        LOG.info("[PRODUCT] [QUESTION] Sending email to {}, from: {}, subject: {}, body: {}", email.getTo(), email.getFrom(), email.getSubject() ,email.getBody());
    }
}

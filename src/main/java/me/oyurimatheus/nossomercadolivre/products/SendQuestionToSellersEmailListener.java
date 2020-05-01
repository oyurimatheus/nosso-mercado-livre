package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailRepository;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class SendQuestionToSellersEmailListener {

    private final EmailService sendEmail;
    private final EmailRepository emailRepository;

    SendQuestionToSellersEmailListener(EmailService sendEmail,
                                       EmailRepository emailRepository) {
        this.sendEmail = sendEmail;
        this.emailRepository = emailRepository;
    }

    @EventListener
    void listen(QuestionEvent questionEvent) {

        // TODO: Apply I18N in this messages?
        var subject = " You have a new question";
        var body = questionEvent.getTitle() + " in " + questionEvent.getProductUri();

        Email email = Email.to(questionEvent.getSellersEmail())
                           .from(questionEvent.getPossibleBuyer())
                           .subject(subject)
                           .body(body)
                           .product(questionEvent.getProduct())
                           .build();

        sendEmail.send(email);
        emailRepository.save(email);
    }
}

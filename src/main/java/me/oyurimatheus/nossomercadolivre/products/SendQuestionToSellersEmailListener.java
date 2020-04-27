package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.shared.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class SendQuestionToSellersEmailListener {

    private final EmailService sendEmail;

    SendQuestionToSellersEmailListener(EmailService sendEmail) {
        this.sendEmail = sendEmail;
    }

    @EventListener
    void listen(QuestionEvent question) {

        // TODO: Apply I18N in this messages?
        var subject = " You have a new question";
        var body = question.getTitle() + " in " + question.getProductUri();

        Email email = Email.getTo(question.getSellersEmail())
                           .from(question.getPossibleBuyer())
                           .subject(subject)
                           .body(body)
                           .build();

        sendEmail.send(email);
    }
}

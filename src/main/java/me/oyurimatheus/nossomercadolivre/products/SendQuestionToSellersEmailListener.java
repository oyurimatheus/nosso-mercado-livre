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
    private final QuestionRepository questionRepository;

    SendQuestionToSellersEmailListener(EmailService sendEmail,
                                       EmailRepository emailRepository,
                                       QuestionRepository questionRepository) {
        this.sendEmail = sendEmail;
        this.emailRepository = emailRepository;
        this.questionRepository = questionRepository;
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
                           .build();

        sendEmail.send(email);
        emailRepository.save(email);
        Question question  = questionRepository.findById(questionEvent.getId()).get();
        question.setEmail(email);
        questionRepository.save(question);
    }
}

package me.oyurimatheus.nossomercadolivre.shared.email;


import me.oyurimatheus.nossomercadolivre.products.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * This class represents the email in the system form
 */
@Table(name = "emails")
@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "email_receiver")
    @NotBlank
    @javax.validation.constraints.Email
    private String to;

    @Column(name = "email_sender")
    @NotBlank
    @javax.validation.constraints.Email
    private String from;

    @Column(name = "email_subject")
    @NotBlank
    private String subject;

    @Column(name = "email_body")
    @NotBlank
    private String body;

    @JoinColumn(name = "product_id")
    @ManyToOne
    @NotNull
    private Product product;

    @Column(name = "email_sent_at")
    @PastOrPresent
    @NotNull
    private LocalDateTime sentAt = now();

    /**
     * @param to the email receiver
     * @param from the emails sender
     * @param subject the emails subject
     * @param body the emails body
     */
    private Email(String to,
                 String from,
                 String subject,
                 String body,
                 Product product) {


        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.product = product;
    }

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private Email() { }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    /**
     *
     * @param to the emails receiver
     * @return an {@link EmailTo} builder chain
     */
    public static EmailTo to(@javax.validation.constraints.Email String to) {
        return new EmailTo(to);
    }

    public static class EmailTo {
        private final String to;

        private EmailTo(@javax.validation.constraints.Email String to) {
            this.to = to;
        }

        public EmailToAndFrom from(String from) {
            return new EmailToAndFrom(from);
        }

        public class EmailToAndFrom {

            private final String from;

            private EmailToAndFrom(String from) {
                this.from = from;
            }

            public EmailWithSubject subject(String subject) {
                return new EmailWithSubject(subject);
            }

            public class EmailWithSubject {
                private final String subject;

                private EmailWithSubject(String subject) {
                    this.subject = subject;
                }

                public EmailWithBody body(String body) {
                    return new EmailWithBody(body);
                }

                public class EmailWithBody {

                    private final String body;

                    private EmailWithBody(String body) {
                        this.body = body;
                    }

                    public EmailWithBodyAndProduct product(Product product) {
                        return new EmailWithBodyAndProduct(product);
                    }

                    public class EmailWithBodyAndProduct {

                        private final Product product;

                        private EmailWithBodyAndProduct(Product product) {
                            this.product = product;
                        }

                        public Email build() {
                            return new Email(to, from, subject, body, product);
                        }
                    }
                }
            }
        }
    }
}

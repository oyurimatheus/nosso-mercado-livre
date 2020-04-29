package me.oyurimatheus.nossomercadolivre.shared.email;


/**
 * This class represents the email in the system form
 */
public class Email {

    private final String to;
    private final String from;
    private final String subject;
    private final String body;

    /**
     * @param to the email receiver
     * @param from the emails sender
     * @param subject the emails subject
     * @param body the emails body
     */
    private Email(String to,
                 String from,
                 String subject,
                 String body) {


        this.to = to;
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

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

                    public Email build() {
                        return new Email(to, from, subject, body);
                    }
                }
            }
        }
    }
}

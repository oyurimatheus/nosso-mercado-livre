package me.oyurimatheus.nossomercadolivre.products;

import me.oyurimatheus.nossomercadolivre.shared.email.Email;
import me.oyurimatheus.nossomercadolivre.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.util.Assert.hasText;

@Table(name = "products_questions")
@Entity
class Question {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @NotBlank
    @Column(name = "question_title")
    private String title;


    @ManyToOne
    @JoinColumn(name = "question_user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_product_id")
    @NotNull
    private Product product;

    @OneToOne
    @JoinColumn(name = "question_email_id")
    private Email email;

    @PastOrPresent
    @Column(name = "question_created_at")
    private LocalDateTime createdAt = now();

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private Question(){ }


    Question(@NotBlank String title,
             @NotNull User user,
             @NotNull Product product) {

        hasText(title, "title must not be blank");
        requireNonNull(user, "user must not be null");
        requireNonNull(product, "product must not be null");

        this.title = title;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}

package me.oyurimatheus.nossomercadolivre.users;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "users")
@Entity
class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @NotEmpty
    @Column(name = "user_email", unique = true)
    private String email;

    @Size(min = 6)
    @Column(name = "user_password")
    private String password;

    @PastOrPresent
    @CreationTimestamp
    private LocalDateTime createdAt = now();

    public User(@Email @NotEmpty String email,
                @NotNull Password password) {
        this.email = email;
        this.password = password.get();
    }

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private User() { }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .add("createdAt=" + createdAt)
                .toString();
    }
}

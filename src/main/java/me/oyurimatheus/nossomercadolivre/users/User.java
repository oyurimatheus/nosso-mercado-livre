package me.oyurimatheus.nossomercadolivre.users;

import me.oyurimatheus.nossomercadolivre.products.Product;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "users")
@Entity
public class User implements UserDetails {

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

    @OneToMany(mappedBy = "user")
    private List<Product> products;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

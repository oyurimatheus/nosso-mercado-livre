package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

public class Password {

    private final String password;

    private Password(@NotNull String rawPassword) {
        requireNonNull(rawPassword, "password must not be null");

        this.password = new BCryptPasswordEncoder().encode(rawPassword);
    }

    public static Password encode(@NotNull String rawString) {
        return new Password(rawString);
    }

    public String get() {
        return password;
    }
}

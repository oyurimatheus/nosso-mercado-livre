package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

class Password {

    private final String password;

    Password(@NotNull String password) {
        requireNonNull(password, "password must not be null");

        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String get() {
        return password;
    }
}

package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class Password {

    private final String password;

    Password(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String get() {
        return password;
    }
}

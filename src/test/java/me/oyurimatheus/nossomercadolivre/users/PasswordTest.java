package me.oyurimatheus.nossomercadolivre.users;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void shouldReturnAnEncryptedStringARawStringIsPassed() {
        String rawPassword = "123456";
        Password password = new Password(rawPassword);

        assertThat(password.get()).isNotEmpty();
        assertThat(encoder.matches(rawPassword, password.get())).isTrue();
    }

    @Test
    void shouldThrowANullPointerExceptionWhenANullPasswordIsPassed() {
        assertThatThrownBy(() -> new Password(null))
                         .hasMessage("password must not be null")
                         .isInstanceOf(NullPointerException.class);
    }

}
package me.oyurimatheus.nossomercadolivre.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class NewUserRequestToUserConverterTest {

    private NewUserRequestToUserConverter converter;

    @BeforeEach
    void setUp() {
        this.converter = new NewUserRequestToUserConverter();
    }

    @Test
    void shouldThrowAnNullPointerWhenANullParameterIsPassedToConvertMethod() {
        assertThatThrownBy(() -> converter.convert(null))
                                          .hasMessage("newUser must not be null")
                                          .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldConvertANewUserRequestWhenAValidParameterIsPassed() {
        String login = "test@email.com";
        String password = "123456";

        var request = new NewUserRequest();
        request.setLogin(login);
        request.setPassword(password);

        var user = converter.convert(request);

        assertThat(user.getEmail()).isEqualTo(login);
        assertThat(user.getPassword()).hasSizeGreaterThan(6);
        assertThat(user.getCreatedAt()).isBeforeOrEqualTo(now());

    }
}
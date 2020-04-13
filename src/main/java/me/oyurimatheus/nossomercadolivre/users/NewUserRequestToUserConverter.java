package me.oyurimatheus.nossomercadolivre.users;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

@Component
class NewUserRequestToUserConverter {

    public User convert(@NotNull NewUserRequest newUser) {
        requireNonNull(newUser, "newUser must not be null");

        Password password = new Password(newUser.getPassword());

        return new User(newUser.getLogin(), password);
    }
}

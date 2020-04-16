package me.oyurimatheus.nossomercadolivre.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

class NewUserRequest {

    @Email
    @NotEmpty
    private String login;

    @Size(min = 6)
    private String password;

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    NewUserRequest() { }

    NewUserRequest(@Email @NotEmpty String login,
                   @Size(min = 6) String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NewUserRequest.class.getSimpleName() + "[", "]")
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .toString();
    }
}

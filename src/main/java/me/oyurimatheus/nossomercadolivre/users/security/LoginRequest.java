package me.oyurimatheus.nossomercadolivre.users.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

class LoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    LoginRequest() {
    }

    public LoginRequest(@NotEmpty String email,
                        @NotEmpty String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken build() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}

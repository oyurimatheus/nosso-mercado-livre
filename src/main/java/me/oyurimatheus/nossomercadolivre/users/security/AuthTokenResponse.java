package me.oyurimatheus.nossomercadolivre.users.security;

class AuthTokenResponse {

    private final String tokenType;
    private final String token;

    public AuthTokenResponse(String tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }
}

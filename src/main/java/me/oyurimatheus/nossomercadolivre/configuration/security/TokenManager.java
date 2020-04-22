package me.oyurimatheus.nossomercadolivre.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import me.oyurimatheus.nossomercadolivre.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
public
class TokenManager {

    @Value("${nosso-mercado.jwt.secret}")
    private String secret;

    @Value("${nosso-mercado.jwt.expiration}")
    private long expirationInMillis;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        var now = new Date();
        var expiration = new Date(now.getTime() + expirationInMillis);

        return Jwts.builder()
                .setIssuer("Nosso mercado livre")
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(HS256, secret)
                .compact();
    }

    public boolean isValid(String jwt) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String jwt) {
        Claims claims = Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(jwt)
                            .getBody();

        return Long.parseLong(claims.getSubject());
    }
}

package me.oyurimatheus.nossomercadolivre.users.security;

import me.oyurimatheus.nossomercadolivre.configuration.security.TokenManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
class UserAuthController {

    private final AuthenticationManager authManager;

    private final TokenManager tokenManager;

    UserAuthController(AuthenticationManager authManager,
                       TokenManager tokenManager) {
        this.authManager = authManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping
    public ResponseEntity<AuthTokenResponse> authenticate(@RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.build();

        try {
            Authentication authentication = authManager.authenticate(authenticationToken);
            String token = tokenManager.generateToken(authentication);

            AuthTokenResponse tokenResponse = new AuthTokenResponse("Bearer", token);
            return ok(tokenResponse);

        } catch (AuthenticationException e) {
            return badRequest().build();
        }

    }
}

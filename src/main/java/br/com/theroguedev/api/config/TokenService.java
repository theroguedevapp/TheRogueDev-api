package br.com.theroguedev.api.config;

import br.com.theroguedev.api.user.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.flywaydb.core.internal.license.FlywayJWTValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenService {

    @Value("${the-rougue-dev.security.secret}")
    private String secret;

    @Value("${the-rougue-dev.security.cookie-secure}")
    private boolean cookieSecure;

    public String gerenateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getSystemRole().getName())
                .withClaim("id", user.getId().toString())
                .withExpiresAt(Instant.now().plusSeconds(28800)) //8 horas
                .withIssuedAt(Instant.now())
                .sign(algorithm);

    }

    public Optional<JWTUserData> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData
                    .builder()
                    .email(jwt.getSubject())
                    .username(jwt.getClaim("username").asString())
                    .role(jwt.getClaim("role").asString())
                    .id(UUID.fromString(jwt.getClaim("id").asString()))
                    .build());

        } catch (JwtValidationException exception) {
            return Optional.empty();
        }
    }

    public ResponseCookie generateCookie(String jwtToken) {
        return  ResponseCookie.from("session", jwtToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .sameSite("Strict")
                .maxAge(28800) // 8 horas
                .build();
    }

}

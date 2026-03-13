package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.config.security.JWTUserData;
import br.com.theroguedev.api.exceptions.UnauthorizedException;
import br.com.theroguedev.api.user.dto.request.LoginRequest;
import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Value("${jwt.cookie-secure}")
    private boolean cookieSecure;

    @Value("${jwt.expires-in}")
    private Long expiresIn;

    @Transactional
    public String login(LoginRequest request) {

        Optional<User> optUser = userRepository.findByEmail(request.email());

        if (optUser.isEmpty() || !isPasswordCorrect(request.password(), optUser.get().getPassword())) {
            throw new BadCredentialsException("Email ou senha incorretos!");
        }

        User user = optUser.get();

        List<String> scopes = user.getSystemRole().getPermissions().stream()
                .map(Permission::getName)
                .toList();

        return generateTokenForOAuthUser(user, scopes);
    }


    public String loginOAuth2User(Map<String, Object> userAttributes) {
        String email = (String) userAttributes.get("email");
        String name = (String) userAttributes.get("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setPassword("");
            return userService.save(newUser, name);
        });

        List<String> scopes = user.getSystemRole().getPermissions().stream()
                .map(Permission::getName)
                .toList();

        return generateTokenForOAuthUser(user, scopes);
    }

    public JWTUserData validateUserAccess(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        if (!Objects.equals(userData.username(), username)) {
            throw new UnauthorizedException("Usuário não tem permissão para realizar essa ação");
        }

        return userData;
    }

    private boolean isPasswordCorrect(String password, String savedPassword) {
        return passwordEncoder.matches(password, savedPassword);
    }

    public ResponseCookie generateCookie(String jwtToken) {
        return ResponseCookie.from("session", jwtToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .sameSite("Strict")
                .maxAge(expiresIn)
                .build();
    }

    public String generateTokenForOAuthUser(User user, List<String> scopes) {
        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("the_rogue_dev_api")
                .subject(user.getEmail())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .issuedAt(Instant.now())
                .claim("username", user.getUsername())
                .claim("role", user.getSystemRole().getName())
                .claim("id", user.getId().toString())
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();
    }
}

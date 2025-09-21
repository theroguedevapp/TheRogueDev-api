package br.com.theroguedev.api.user.controller;

import br.com.theroguedev.api.config.TokenService;
import br.com.theroguedev.api.exceptions.UsernameOrPasswordInvalidException;
import br.com.theroguedev.api.user.dto.request.LoginRequest;
import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.AuthenticatedUserResponse;
import br.com.theroguedev.api.user.dto.response.LoginResponse;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.mapper.UserMapper;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {

            UsernamePasswordAuthenticationToken usernameAndPassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authentication = authenticationManager.authenticate(usernameAndPassword);

            User user = (User) authentication.getPrincipal();

            String token = tokenService.gerenateToken(user);

            ResponseCookie cookie = tokenService.generateCookie(token);

            AuthenticatedUserResponse userResponse = userMapper.toAuthenticatedUserResponse(user);

            return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new LoginResponse(userResponse));


        } catch (BadCredentialsException exception) {
            throw new UsernameOrPasswordInvalidException("Usuário ou senha inválido.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticatedUserResponse> save(@RequestBody UserRequest request) {
        User newUser = userMapper.toUser(request);
        User savedUser = userService.save(newUser, request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toAuthenticatedUserResponse(savedUser));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("session", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}

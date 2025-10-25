package br.com.theroguedev.api.user.controller;

import br.com.theroguedev.api.user.controller.doc.AuthControllerDoc;
import br.com.theroguedev.api.user.dto.request.LoginRequest;
import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.AuthenticatedUserResponse;
import br.com.theroguedev.api.user.dto.response.LoginResponse;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.mapper.UserMapper;
import br.com.theroguedev.api.user.service.AuthService;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = authService.login(request);

        ResponseCookie cookie = authService.generateCookie(token);

        LoginResponse loginResponse = LoginResponse.builder()
                .message("Login Realizado com sucesso!")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(loginResponse);
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

package br.com.theroguedev.api.user.controller;

import br.com.theroguedev.api.config.security.annotation.read.CanReadUser;
import br.com.theroguedev.api.user.controller.doc.UserControllerDoc;
import br.com.theroguedev.api.user.dto.response.AuthenticatedUserResponse;
import br.com.theroguedev.api.user.dto.response.UserResponse;
import br.com.theroguedev.api.user.mapper.UserMapper;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @CanReadUser
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList());
    }

    @GetMapping("/id/{id}")
    @CanReadUser
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    @CanReadUser
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUserResponse> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {

        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return userService.findById(UUID.fromString(jwt.getClaimAsString("id")))
                .map(user -> ResponseEntity.ok(userMapper.toAuthenticatedUserResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

}

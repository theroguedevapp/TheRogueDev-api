package br.com.theroguedev.api.user.controller;


import br.com.theroguedev.api.user.controller.doc.UserControllerDoc;
import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.UserResponse;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.mapper.UserMapper;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user:get_all')")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList());
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user:get_by_id')")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user:get_by_username')")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> ResponseEntity.ok(userMapper.toResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user:create')")
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest request) {
        User newUser = userMapper.toUser(request);
        User savedUser = userService.save(newUser, request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(savedUser));
    }

}

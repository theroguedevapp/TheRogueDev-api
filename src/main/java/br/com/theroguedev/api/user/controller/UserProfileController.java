package br.com.theroguedev.api.user.controller;


import br.com.theroguedev.api.config.JWTUserData;
import br.com.theroguedev.api.user.controller.doc.UserProfileControllerDoc;
import br.com.theroguedev.api.user.dto.request.UserProfileRequest;
import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import br.com.theroguedev.api.user.dto.response.UserResponse;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.mapper.UserMapper;
import br.com.theroguedev.api.user.mapper.UserProfileMapper;
import br.com.theroguedev.api.user.service.UserProfileService;
import br.com.theroguedev.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class UserProfileController implements UserProfileControllerDoc {

    private final UserProfileService userProfileService;
    private final UserProfileMapper userProfileMapper;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user_profile:get_all')")
    public ResponseEntity<List<UserProfileResponse>> getAll() {
        return ResponseEntity.ok(userProfileService.findAll()
                .stream()
                .map(userProfileMapper::toResponse)
                .toList());
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user_profile:get_by_id')")
    public ResponseEntity<UserProfileResponse> getById(@PathVariable UUID id) {
        return userProfileService.findById(id)
                .map(userProfile -> ResponseEntity.ok(userProfileMapper.toResponse(userProfile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('user_profile:get_by_username')")
    public ResponseEntity<UserProfileResponse> getByUsername(@PathVariable String username) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) authentication.getPrincipal();


        return userProfileService.findById(userData.id())
                .map(userProfile -> ResponseEntity.ok(userProfileMapper.toResponse(userProfile)))
                .orElse(ResponseEntity.notFound().build());
    }

}

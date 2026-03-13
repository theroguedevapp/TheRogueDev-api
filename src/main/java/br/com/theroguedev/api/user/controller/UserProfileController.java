package br.com.theroguedev.api.user.controller;

import br.com.theroguedev.api.config.security.JWTUserData;
import br.com.theroguedev.api.config.security.annotation.read.CanReadUserProfile;
import br.com.theroguedev.api.config.security.annotation.update.CanUpdateUserProfile;
import br.com.theroguedev.api.user.controller.doc.UserProfileControllerDoc;
import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.mapper.UserProfileMapper;
import br.com.theroguedev.api.user.service.AuthService;
import br.com.theroguedev.api.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class UserProfileController implements UserProfileControllerDoc {

    private final UserProfileService userProfileService;
    private final UserProfileMapper userProfileMapper;
    private final AuthService authService;

    @GetMapping
    @CanReadUserProfile
    public ResponseEntity<List<UserProfileResponse>> getAll() {
        return ResponseEntity.ok(userProfileService.findAll()
                .stream()
                .map(userProfileMapper::toResponse)
                .toList());
    }

    @GetMapping("/id/{id}")
    @CanReadUserProfile
    public ResponseEntity<UserProfileResponse> getById(@PathVariable UUID id) {
        return userProfileService.findById(id)
                .map(userProfile -> ResponseEntity.ok(userProfileMapper.toResponse(userProfile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    @CanReadUserProfile
    public ResponseEntity<UserProfileResponse> getByUsername(@PathVariable String username) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return userProfileService.findById(userData.id())
                .map(userProfile -> ResponseEntity.ok(userProfileMapper.toResponse(userProfile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/pic/{username}")
    @CanUpdateUserProfile
    public ResponseEntity<UserProfileResponse> changeProfilePic(@PathVariable String username, @RequestParam("file") MultipartFile file) {

        JWTUserData userData = authService.validateUserAccess(username);
        UserProfile userProfile = userProfileService.changeProfilePic(file, userData.id());

        return ResponseEntity.ok(userProfileMapper.toResponse(userProfile));
    }

    @PatchMapping("/banner/{username}")
    @CanUpdateUserProfile
    public ResponseEntity<UserProfileResponse> changeProfileBanner(@PathVariable String username, @RequestParam("file") MultipartFile file) {

        JWTUserData userData = authService.validateUserAccess(username);
        UserProfile userProfile = userProfileService.changeProfileBanner(file, userData.id());

        return ResponseEntity.ok(userProfileMapper.toResponse(userProfile));
    }

}

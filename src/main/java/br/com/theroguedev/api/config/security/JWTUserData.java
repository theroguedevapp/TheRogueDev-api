package br.com.theroguedev.api.config.security;

import lombok.Builder;

import java.util.UUID;

@Builder
public record JWTUserData(String email, String username, String role, UUID id) {
}

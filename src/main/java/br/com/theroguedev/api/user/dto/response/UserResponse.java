package br.com.theroguedev.api.user.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(
        String username,
        String email,
        String isActive,
        SystemRoleResponse systemRole
) {
}

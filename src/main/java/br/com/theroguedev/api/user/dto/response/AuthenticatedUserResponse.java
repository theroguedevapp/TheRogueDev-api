package br.com.theroguedev.api.user.dto.response;

import lombok.Builder;

@Builder
public record AuthenticatedUserResponse(
        String username,
        String role
) {
}

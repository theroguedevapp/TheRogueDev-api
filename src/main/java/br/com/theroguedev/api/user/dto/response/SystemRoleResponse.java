package br.com.theroguedev.api.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SystemRoleResponse(
        Long id,
        String name,
        String description,
        List<PermissionResponse> permissions
) {
}

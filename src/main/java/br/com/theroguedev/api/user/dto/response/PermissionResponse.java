package br.com.theroguedev.api.user.dto.response;

import lombok.Builder;

@Builder
public record PermissionResponse(
        Long id,
        String name,
        String description
) {
}

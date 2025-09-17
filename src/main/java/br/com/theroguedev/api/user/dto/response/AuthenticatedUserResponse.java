package br.com.theroguedev.api.user.dto.response;

import br.com.theroguedev.api.user.entity.SystemRole;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthenticatedUserResponse(
    UUID id,
    String username,
    SystemRole role
) {
}

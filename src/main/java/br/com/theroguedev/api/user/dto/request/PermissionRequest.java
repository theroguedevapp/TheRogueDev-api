package br.com.theroguedev.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
        @NotBlank(message = "Nome da permissão é obrigatória")
        String name,
        String description
) {

}

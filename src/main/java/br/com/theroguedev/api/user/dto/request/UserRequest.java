package br.com.theroguedev.api.user.dto.request;

import br.com.theroguedev.api.user.dto.response.SystemRoleResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String username,
        @NotBlank(message = "Senha é obrigatória")
        @Min(6)
        String password,
        @NotBlank(message = "E-mail é obrigatório")
        @Email
        String email,
        @NotBlank(message = "Nome é obrigatório")
        String name
) {
}

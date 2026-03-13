package br.com.theroguedev.api.user.dto.request;

import br.com.theroguedev.api.user.dto.response.SystemRoleResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Nome do usuário é obrigatório")
        String username,
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String password,
        @NotBlank(message = "E-mail é obrigatório")
        @Email
        String email,
        @NotBlank(message = "Nome é obrigatório")
        String name
) {
}

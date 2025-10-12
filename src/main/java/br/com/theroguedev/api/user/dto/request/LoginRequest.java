package br.com.theroguedev.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email é obrigatório")
        @Email
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String password
) {
}

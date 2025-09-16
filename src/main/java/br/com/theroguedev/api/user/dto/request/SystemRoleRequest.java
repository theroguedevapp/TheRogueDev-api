package br.com.theroguedev.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SystemRoleRequest(
        @NotBlank(message = "Nome do cargo é obrigatório")
        String name,
        String description,
        List<Long> permissions
) {

}

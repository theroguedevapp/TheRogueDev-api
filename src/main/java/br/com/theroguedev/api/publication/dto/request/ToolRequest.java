package br.com.theroguedev.api.publication.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ToolRequest(
        @NotBlank(message = "name é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String name,
        String description,
        @Size(max = 9, message = "A cor deve ter no máximo 9 caracteres.")
        String hexColor
) {
}

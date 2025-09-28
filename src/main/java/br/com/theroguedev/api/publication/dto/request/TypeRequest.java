package br.com.theroguedev.api.publication.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TypeRequest(
        @NotBlank(message = "name é obrigatório.")
        @Min(3)
        @Max(100)
        String name,
        String description
) {
}

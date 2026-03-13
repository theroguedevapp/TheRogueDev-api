package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TransactionTypeRequest(
        @NotBlank(message = "name é obrigatório.")
        @Size(min = 3, max = 100)
        String name,
        String description
) {
}

package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record VirtualCurrencyRequest(
        @NotBlank(message = "name é obrigatório.")
        @Size(min = 3, max = 100)
        String name,
        @NotBlank(message = "body é obrigatório.")
        @Size(min = 2, max = 10)
        String symbol,
        String description,
        Double exchangeRate
) {
}

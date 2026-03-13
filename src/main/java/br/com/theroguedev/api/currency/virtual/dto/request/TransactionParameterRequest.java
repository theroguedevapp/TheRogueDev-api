package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionParameterRequest(
        @NotBlank(message = "name é obrigatório.")
        String name,
        String description,
        Long cost,
        Long reward,
        @NotNull(message = "virtualCurrencyId é obrigatório.")
        Long virtualCurrencyId
) {
}

package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserWalletRequest(
        @NotNull(message = "userId é obrigatório.")
        UUID userId,
        @NotNull(message = "virtualCurrencyId é obrigatório.")
        Long virtualCurrencyId
) {
}

package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TransactionRequest(
        @NotNull(message = "userId é obrigatório.")
        Long amount,
        String description,
        @NotNull(message = "virtualCurrencyId é obrigatório.")
        Long virtualCurrencyId,
        @NotNull(message = "virtualCurrencyId é obrigatório.")
        Long transactionTypeId,
        UUID relatedForumPublicationId,
        UUID relatedUserId
) {
}

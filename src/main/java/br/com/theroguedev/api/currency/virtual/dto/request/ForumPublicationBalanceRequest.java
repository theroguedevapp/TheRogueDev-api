package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ForumPublicationBalanceRequest(
        @NotNull(message = "forumPublicationId é obrigatório.")
        UUID forumPublicationId,
        @NotNull(message = "virtualCurrencyId é obrigatório.")
        Long virtualCurrencyId
) {
}

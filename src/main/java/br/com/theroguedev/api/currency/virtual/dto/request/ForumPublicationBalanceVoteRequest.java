package br.com.theroguedev.api.currency.virtual.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ForumPublicationBalanceVoteRequest(
        @NotEmpty(message = "forumPublicationSlug é obrigatório.")
        String forumPublicationSlug
) {
}

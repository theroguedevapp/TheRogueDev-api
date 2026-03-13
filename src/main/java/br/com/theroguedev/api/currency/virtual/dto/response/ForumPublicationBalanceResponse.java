package br.com.theroguedev.api.currency.virtual.dto.response;

import br.com.theroguedev.api.publication.dto.response.ForumPublicationResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ForumPublicationBalanceResponse(
        UUID id,
        Long credit,
        Long debit,
        ForumPublicationResponse forumPublication,
        VirtualCurrencyResponse virtualCurrency
) {
}

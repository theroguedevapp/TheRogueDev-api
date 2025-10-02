package br.com.theroguedev.api.currency.virtual.dto.response;

import br.com.theroguedev.api.publication.dto.response.ForumPublicationResponse;
import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserWalletResponse(
        UUID id,
        Long balance,
        UserProfileResponse userProfile,
        VirtualCurrencyResponse virtualCurrency
) {
}

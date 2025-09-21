package br.com.theroguedev.api.user.dto.response;

import lombok.Builder;

@Builder
public record UserProfileResponse(
        String name,
        String profilePicUrl,
        String profileBannerUrl,
        String biography,
        String discord,
        String linkedin,
        String github,
        String personalWebsite
) {
}

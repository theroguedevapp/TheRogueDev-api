package br.com.theroguedev.api.user.dto.request;

public record UserProfileRequest(
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

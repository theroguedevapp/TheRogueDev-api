package br.com.theroguedev.api.currency.virtual.mapper;

import br.com.theroguedev.api.currency.virtual.dto.request.UserWalletRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.UserWalletResponse;
import br.com.theroguedev.api.currency.virtual.entity.UserWallet;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.mapper.UserProfileMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {UserProfileMapper.class}
)
public interface UserWalletMapper {

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "virtualCurrency", source = "virtualCurrencyId")
    UserWallet toUserWallet(UserWalletRequest request);

    @Mapping(target = "userProfile", source = "user")
    UserWalletResponse toResponse(UserWallet entity);

    default User mapUserIdToUser(UUID userId) {
        return User.builder().id(userId).build();
    }

    default VirtualCurrency mapVirtualCurrencyIdToVirtualCurrency(Long virtualCurrencyId) {
        return VirtualCurrency.builder().id(virtualCurrencyId).build();
    }

    default UserProfileResponse mapUserToUserProfileResponse(User user) {
        if (user == null || user.getUserProfile() == null) return null;
        UserProfile userProfile = user.getUserProfile();
        return UserProfileResponse.builder()
                .name(userProfile.getName())
                .profilePicUrl(userProfile.getProfilePicUrl())
                .profileBannerUrl(userProfile.getProfileBannerUrl())
                .biography(userProfile.getBiography())
                .discord(userProfile.getDiscord())
                .linkedin(userProfile.getLinkedin())
                .github(userProfile.getGithub())
                .personalWebsite(userProfile.getGithub())
                .build();
    }
}
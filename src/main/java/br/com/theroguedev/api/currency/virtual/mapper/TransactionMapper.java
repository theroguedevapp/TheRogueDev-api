package br.com.theroguedev.api.currency.virtual.mapper;

import br.com.theroguedev.api.currency.virtual.dto.request.TransactionRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionResponse;
import br.com.theroguedev.api.currency.virtual.entity.Transaction;
import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.publication.entity.ForumPublication;
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
public interface TransactionMapper {

    @Mapping(target = "virtualCurrency", source = "virtualCurrencyId")
    @Mapping(target = "transactionType", source = "transactionTypeId")
    @Mapping(target = "relatedForumPublication", source = "relatedForumPublicationId")
    @Mapping(target = "relatedUser", source = "relatedUserId")
    Transaction toTransaction(TransactionRequest request);

    @Mapping(target = "userProfile", source = "user")
    @Mapping(target = "relatedUserProfile", source = "relatedUser")
    TransactionResponse toResponse(Transaction entity);

    default VirtualCurrency mapVirtualCurrencyIdToVirtualCurrency(Long virtualCurrencyId) {
        return VirtualCurrency.builder().id(virtualCurrencyId).build();
    }

    default TransactionType mapTransactionTypeIdToTransactionType(Long transactionTypeId) {
        return TransactionType.builder().id(transactionTypeId).build();
    }

    default User mapUserIdToUser(UUID userId) {
        if (userId == null) return null;
        return User.builder().id(userId).build();
    }

    default ForumPublication mapForumPublicationIdToForumPublication(UUID relatedForumPublicationId) {
        if (relatedForumPublicationId == null) return null;
        return ForumPublication.builder().id(relatedForumPublicationId).build();
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
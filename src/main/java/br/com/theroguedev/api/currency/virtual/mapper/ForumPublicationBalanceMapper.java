package br.com.theroguedev.api.currency.virtual.mapper;

import br.com.theroguedev.api.currency.virtual.dto.request.ForumPublicationBalanceRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.ForumPublicationBalanceResponse;
import br.com.theroguedev.api.currency.virtual.entity.ForumPublicationBalance;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.user.mapper.UserProfileMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {UserProfileMapper.class}
)
public interface ForumPublicationBalanceMapper {

    @Mapping(target = "forumPublication", source = "forumPublicationId")
    @Mapping(target = "virtualCurrency", source = "virtualCurrencyId")
    ForumPublicationBalance toForumPublicationBalance(ForumPublicationBalanceRequest request);

    ForumPublicationBalanceResponse toResponse(ForumPublicationBalance entity);

    default ForumPublication mapForumPublicationIdToForumPublication(UUID forumPublicationId) {
        return ForumPublication.builder().id(forumPublicationId).build();
    }

    default VirtualCurrency mapVirtualCurrencyIdToVirtualCurrency(Long virtualCurrencyId) {
        return VirtualCurrency.builder().id(virtualCurrencyId).build();
    }
}
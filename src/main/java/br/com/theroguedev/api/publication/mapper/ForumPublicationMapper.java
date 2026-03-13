package br.com.theroguedev.api.publication.mapper;

import br.com.theroguedev.api.publication.dto.request.ForumPublicationRequest;
import br.com.theroguedev.api.publication.dto.response.ForumPublicationResponse;
import br.com.theroguedev.api.publication.dto.response.ForumPublicationWithChildrenResponse;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.publication.entity.Tool;
import br.com.theroguedev.api.publication.entity.Topic;
import br.com.theroguedev.api.publication.entity.Type;
import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.mapper.UserProfileMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {UserProfileMapper.class}
)
public interface ForumPublicationMapper {

    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapParent")
    @Mapping(target = "type", source = "typeId")
    @Mapping(target = "tools", source = "toolIds")
    @Mapping(target = "topics", source = "topicIds")
    ForumPublication toForumPublication(ForumPublicationRequest request);

    @Mapping(target = "submittedBy", source = "submittedBy")
    @Mapping(target = "authors", source = "authors")
    ForumPublicationResponse toResponse(ForumPublication entity);

    @Mapping(target = "submittedBy", source = "submittedBy")
    @Mapping(target = "authors", source = "authors")
    @Mapping(target = "children", source = "children")
    ForumPublicationWithChildrenResponse toResponseWithChildren(ForumPublication entity);

    @Named("mapParent")
    default ForumPublication mapParent(UUID parentId) {
        if (parentId == null) return null;
        ForumPublication parent = new ForumPublication();
        parent.setId(parentId);
        return parent;
    }

    default List<ForumPublicationResponse> mapChildren(List<ForumPublication> children) {
        if (children == null) return List.of();
        return children.stream()
                .map(this::toResponse)
                .toList();
    }

    default ForumPublication mapParentIdToParent(UUID parentId) {
        return ForumPublication.builder().id(parentId).build();
    }

    default Type mapTypeIdToType(Long typeId) {
        return Type.builder().id(typeId).build();
    }

    default List<Tool> mapToolIdsToTools(List<Long> toolsIds) {
        if (toolsIds == null) return List.of();
        return toolsIds.stream()
                .map(id -> Tool.builder().id(id).build())
                .toList();
    }

    default List<Topic> mapTopicIdsToTopics(List<Long> topicIds) {
        if (topicIds == null) return List.of();
        return topicIds.stream()
                .map(id -> Topic.builder().id(id).build())
                .toList();
    }

    default List<User> mapAuthorUsernamesToAuthors(List<String> authorUsernames) {
        if (authorUsernames == null) return List.of();
        return authorUsernames.stream()
                .map(username -> User.builder().username(username).build())
                .toList();
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

    default List<UserProfileResponse> mapUsersToUserProfiles(List<User> users) {
        if (users == null) return List.of();
        return users.stream()
                .map(this::mapUserToUserProfileResponse)
                .toList();
    }
}
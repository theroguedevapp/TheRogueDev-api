package br.com.theroguedev.api.publication.dto.response;

import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record ForumPublicationWithChildrenResponse(
        UUID id,
        String slug,
        String title,
        String body,
        String imageUrl,
        List<ForumPublicationWithChildrenResponse> children,
        UserProfileResponse submittedBy,
        StatusResponse status,
        TypeResponse type,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<UserProfileResponse> authors,
        List<ToolResponse> tools,
        List<TopicResponse> topics
) {
}

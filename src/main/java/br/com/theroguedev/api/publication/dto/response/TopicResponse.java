package br.com.theroguedev.api.publication.dto.response;

import lombok.Builder;

@Builder
public record TopicResponse(
        Long id,
        String name,
        String description,
        String hexColor,
        String isActive
) {
}

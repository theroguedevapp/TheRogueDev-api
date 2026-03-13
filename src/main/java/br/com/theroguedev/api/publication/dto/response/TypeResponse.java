package br.com.theroguedev.api.publication.dto.response;

import lombok.Builder;

@Builder
public record TypeResponse(
        Long id,
        String name,
        String description,
        String isActive
) {
}

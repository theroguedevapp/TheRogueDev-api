package br.com.theroguedev.api.publication.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record StatusResponse(
        Long id,
        String name,
        String description,
        String isActive
) {
}

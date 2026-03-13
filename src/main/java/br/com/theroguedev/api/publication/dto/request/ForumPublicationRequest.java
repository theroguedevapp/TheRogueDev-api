package br.com.theroguedev.api.publication.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record ForumPublicationRequest(
        @NotBlank(message = "title é obrigatório.")
        String title,
        @NotBlank(message = "body é obrigatório.")
        String body,
        String imageUrl,
        UUID parentId,
        @NotBlank(message = "typeId é obrigatório.")
        Long typeId,
        List<String> authorUsernames,
        List<Long> toolIds,
        List<Long> topicIds
) {
}

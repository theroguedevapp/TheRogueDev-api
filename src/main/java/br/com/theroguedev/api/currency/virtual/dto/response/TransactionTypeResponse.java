package br.com.theroguedev.api.currency.virtual.dto.response;

import lombok.Builder;

@Builder
public record TransactionTypeResponse(
        Long id,
        String name,
        String description
) {
}

package br.com.theroguedev.api.currency.virtual.dto.response;

import lombok.Builder;

@Builder
public record VirtualCurrencyResponse(
        Long id,
        String name,
        String symbol,
        Double exchangeRate,
        String description
) {
}

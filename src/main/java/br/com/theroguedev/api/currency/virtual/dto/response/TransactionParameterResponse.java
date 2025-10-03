package br.com.theroguedev.api.currency.virtual.dto.response;

import lombok.Builder;

@Builder
public record TransactionParameterResponse(
        Long id,
        String name,
        String description,
        Long cost,
        Long reward,
        VirtualCurrencyResponse virtualCurrency
) {
}

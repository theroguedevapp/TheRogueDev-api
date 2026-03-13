package br.com.theroguedev.api.currency.virtual.mapper;

import br.com.theroguedev.api.currency.virtual.dto.request.VirtualCurrencyRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.VirtualCurrencyResponse;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VirtualCurrencyMapper {
    VirtualCurrency toVirtualCurrency(VirtualCurrencyRequest request);

    VirtualCurrencyResponse toResponse(VirtualCurrency entity);
}

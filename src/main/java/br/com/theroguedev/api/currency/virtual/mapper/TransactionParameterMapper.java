package br.com.theroguedev.api.currency.virtual.mapper;

import br.com.theroguedev.api.currency.virtual.dto.request.TransactionParameterRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionParameterResponse;
import br.com.theroguedev.api.currency.virtual.entity.TransactionParameter;
import br.com.theroguedev.api.currency.virtual.entity.VirtualCurrency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionParameterMapper {

    @Mapping(target = "virtualCurrency", source = "virtualCurrencyId")
    TransactionParameter toTransactionParameter(TransactionParameterRequest request);

    TransactionParameterResponse toResponse(TransactionParameter entity);

    default VirtualCurrency mapVirtualCurrencyIdToVirtualCurrency(Long virtualCurrencyId) {
        return VirtualCurrency.builder().id(virtualCurrencyId).build();
    }

}
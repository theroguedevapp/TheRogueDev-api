package br.com.theroguedev.api.currency.virtual.mapper;

import br.com.theroguedev.api.currency.virtual.dto.request.TransactionTypeRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionTypeResponse;
import br.com.theroguedev.api.currency.virtual.entity.TransactionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {
    TransactionType toTransactionType(TransactionTypeRequest request);

    TransactionTypeResponse toResponse(TransactionType entity);
}

package br.com.theroguedev.api.publication.mapper;

import br.com.theroguedev.api.publication.dto.request.StatusRequest;
import br.com.theroguedev.api.publication.dto.response.StatusResponse;
import br.com.theroguedev.api.publication.entity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status toStatus(StatusRequest request);

    StatusResponse toResponse(Status entity);
}
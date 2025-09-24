package br.com.theroguedev.api.publication.mapper;

import br.com.theroguedev.api.publication.dto.request.TopicRequest;
import br.com.theroguedev.api.publication.dto.request.TypeRequest;
import br.com.theroguedev.api.publication.dto.response.TopicResponse;
import br.com.theroguedev.api.publication.dto.response.TypeResponse;
import br.com.theroguedev.api.publication.entity.Topic;
import br.com.theroguedev.api.publication.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    Type toType(TypeRequest request);

    TypeResponse toResponse(Type entity);
}
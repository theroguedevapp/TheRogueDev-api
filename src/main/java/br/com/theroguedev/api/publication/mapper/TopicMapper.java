package br.com.theroguedev.api.publication.mapper;

import br.com.theroguedev.api.publication.dto.request.ToolRequest;
import br.com.theroguedev.api.publication.dto.request.TopicRequest;
import br.com.theroguedev.api.publication.dto.response.ToolResponse;
import br.com.theroguedev.api.publication.dto.response.TopicResponse;
import br.com.theroguedev.api.publication.entity.Tool;
import br.com.theroguedev.api.publication.entity.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    Topic toTopic(TopicRequest request);

    TopicResponse toResponse(Topic entity);
}
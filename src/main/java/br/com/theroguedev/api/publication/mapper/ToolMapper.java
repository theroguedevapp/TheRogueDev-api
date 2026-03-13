package br.com.theroguedev.api.publication.mapper;

import br.com.theroguedev.api.publication.dto.request.StatusRequest;
import br.com.theroguedev.api.publication.dto.request.ToolRequest;
import br.com.theroguedev.api.publication.dto.response.StatusResponse;
import br.com.theroguedev.api.publication.dto.response.ToolResponse;
import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.publication.entity.Tool;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToolMapper {
    Tool toTool(ToolRequest request);

    ToolResponse toResponse(Tool entity);
}
package br.com.theroguedev.api.user.mapper;

import br.com.theroguedev.api.user.dto.request.PermissionRequest;
import br.com.theroguedev.api.user.dto.response.PermissionResponse;
import br.com.theroguedev.api.user.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toResponse(Permission entity);

}

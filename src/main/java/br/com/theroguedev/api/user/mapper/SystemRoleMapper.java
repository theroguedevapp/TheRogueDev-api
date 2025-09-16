package br.com.theroguedev.api.user.mapper;

import br.com.theroguedev.api.user.dto.request.PermissionRequest;
import br.com.theroguedev.api.user.dto.request.SystemRoleRequest;
import br.com.theroguedev.api.user.dto.response.SystemRoleResponse;
import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.entity.SystemRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SystemRoleMapper {

    @Mapping(target = "permissions", source = "permissions")
    SystemRole toSystemRole(SystemRoleRequest request);

    SystemRoleResponse toResponse(SystemRole entity);

    default Permission map(Long id) {
        return Permission.builder()
                .id(id)
                .build();
    }
}
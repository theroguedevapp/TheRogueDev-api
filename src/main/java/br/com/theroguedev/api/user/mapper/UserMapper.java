package br.com.theroguedev.api.user.mapper;

import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.AuthenticatedUserResponse;
import br.com.theroguedev.api.user.dto.response.UserResponse;
import br.com.theroguedev.api.user.entity.SystemRole;
import br.com.theroguedev.api.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {SystemRoleMapper.class})
public interface UserMapper {

    User toUser(UserRequest request);

    UserResponse toResponse(User user);

    @Mapping(target = "role", source = "systemRole", qualifiedByName = "systemRoleToRoleName")
    AuthenticatedUserResponse toAuthenticatedUserResponse(User user);

    @Named("systemRoleToRoleName")
    default String systemRoleToRoleName(SystemRole systemRole) {
        return systemRole != null ? systemRole.getName() : null;
    }
}
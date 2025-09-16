package br.com.theroguedev.api.user.mapper;

import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.UserResponse;
import br.com.theroguedev.api.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SystemRoleMapper.class})
public interface UserMapper {

    User toUser(UserRequest request);

    UserResponse toResponse(User user);
}
package br.com.theroguedev.api.user.mapper;

import br.com.theroguedev.api.user.dto.request.UserProfileRequest;
import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
import br.com.theroguedev.api.user.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileRequest request);
    UserProfileResponse toResponse(UserProfile entity);

}

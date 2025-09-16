package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.user.entity.SystemRole;
import br.com.theroguedev.api.user.entity.User;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final SystemRoleService systemRoleService;
    private final UserProfileService userProfileService;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public User save(User user, String userProfileName) {
        user.setIsActive(true);
        user.setSystemRole(findSystemRoleByName());
        UserProfile profile = UserProfile
                .builder()
                .user(user)
                .name(userProfileName)
                .build();
        user.setUserProfile(profile);
        return repository.save(user);
    }

    private SystemRole findSystemRoleByName() {
        return systemRoleService.findByName("USER").orElse(null);
    }

}

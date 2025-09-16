package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.entity.UserProfile;
import br.com.theroguedev.api.user.repository.PermissionRepository;
import br.com.theroguedev.api.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;

    public List<UserProfile> findAll() {
        return repository.findAll();
    }

    public Optional<UserProfile> findById(UUID id) {
        return repository.findById(id);
    }

}

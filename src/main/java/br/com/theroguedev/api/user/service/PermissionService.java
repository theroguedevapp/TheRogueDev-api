package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;

    public List<Permission> findAll() {
        return repository.findAll();
    }

    public Optional<Permission> findById(Long id) {
        return repository.findById(id);
    }

    public Permission save(Permission permission) {
        permission.setIsActive(true);
        return repository.save(permission);
    }

    @Transactional
    public void registerPermission(String name) {
        if (!repository.existsByName(name)) {
            Permission permission = Permission
                    .builder()
                    .name(name)
                    .isActive(true)
                    .build();
            repository.save(permission);
        }
    }

}

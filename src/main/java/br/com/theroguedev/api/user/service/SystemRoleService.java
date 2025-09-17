package br.com.theroguedev.api.user.service;

import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.entity.SystemRole;
import br.com.theroguedev.api.user.repository.SystemRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemRoleService {

    private final SystemRoleRepository repository;
    private final PermissionService permissionService;


    public List<SystemRole> findAll() {
        return repository.findAll();
    }

    public Optional<SystemRole> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<SystemRole> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public SystemRole save(SystemRole systemRole) {
        systemRole.setIsActive(true);
        systemRole.setPermissions(this.findPermissions(systemRole.getPermissions()));
        return repository.save(systemRole);
    }

    @Transactional
    public Optional<SystemRole> changePermissions(Long systemRoleId, List<Long> permissionsIds) {
        Optional<SystemRole> optSystemRole = repository.findById(systemRoleId);

        if (optSystemRole.isPresent()) {
            List<Permission> permissions = permissionsIds.stream()
                    .map(permissionId -> Permission.builder().id(permissionId).build())
                    .toList();

            List<Permission> permissionsFound = findPermissions(permissions);
            SystemRole systemRole = optSystemRole.get();

            systemRole.setPermissions(permissionsFound);

            repository.save(systemRole);
            return Optional.of(systemRole);
        }

        return Optional.empty();
    }

    private List<Permission> findPermissions(List<Permission> permissions) {
        List<Permission> permissionsFound = new ArrayList<>();
        permissions.forEach(permission -> permissionService.findById(permission.getId()).ifPresent(permissionsFound::add));
        return permissionsFound;
    }

}

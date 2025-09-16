package br.com.theroguedev.api.user.repository;

import br.com.theroguedev.api.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByName(String name);

}

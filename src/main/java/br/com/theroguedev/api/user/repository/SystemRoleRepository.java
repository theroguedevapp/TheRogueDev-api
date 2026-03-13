package br.com.theroguedev.api.user.repository;

import br.com.theroguedev.api.user.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {

    Optional<SystemRole> findByName(String name);
}

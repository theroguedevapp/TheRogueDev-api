package br.com.theroguedev.api.user.repository;

import br.com.theroguedev.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

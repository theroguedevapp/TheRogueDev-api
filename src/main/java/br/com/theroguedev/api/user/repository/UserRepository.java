package br.com.theroguedev.api.user.repository;

import br.com.theroguedev.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<UserDetails> findByEmail(String email);

    Optional<User> findByUsername(String username);

}

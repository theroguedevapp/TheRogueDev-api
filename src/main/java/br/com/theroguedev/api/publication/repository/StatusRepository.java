package br.com.theroguedev.api.publication.repository;

import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.user.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findAllByOrderByNameAsc();

    Optional<Status> findByName(String name);

}

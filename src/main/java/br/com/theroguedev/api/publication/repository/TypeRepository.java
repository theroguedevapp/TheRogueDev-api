package br.com.theroguedev.api.publication.repository;

import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.publication.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    List<Type> findAllByOrderByNameAsc();

}

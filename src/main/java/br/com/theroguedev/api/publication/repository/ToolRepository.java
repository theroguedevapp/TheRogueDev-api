package br.com.theroguedev.api.publication.repository;

import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.publication.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    List<Tool> findAllByOrderByNameAsc();

}

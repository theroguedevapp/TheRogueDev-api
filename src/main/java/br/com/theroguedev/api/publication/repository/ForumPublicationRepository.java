package br.com.theroguedev.api.publication.repository;

import br.com.theroguedev.api.publication.entity.ForumPublication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ForumPublicationRepository extends JpaRepository<ForumPublication, UUID> {

    List<ForumPublication> findAllBySlugStartingWith(String slug);

}


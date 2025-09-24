package br.com.theroguedev.api.publication.repository;

import br.com.theroguedev.api.publication.entity.ForumPublication;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ForumPublicationRepository extends JpaRepository<ForumPublication, UUID> {

    List<ForumPublication> findAllBySlugStartingWith(String slug);

    @EntityGraph(attributePaths = {"children", "children.children", "children.children.children"})
    @Query("SELECT fp FROM ForumPublication fp WHERE fp.id = :id")
    Optional<ForumPublication> findByIdWithChildren(UUID id);
}


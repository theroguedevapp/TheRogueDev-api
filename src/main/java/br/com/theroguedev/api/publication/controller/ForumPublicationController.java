package br.com.theroguedev.api.publication.controller;

import br.com.theroguedev.api.config.JWTUserData;
import br.com.theroguedev.api.publication.controller.doc.ForumPublicationControllerDoc;
import br.com.theroguedev.api.publication.dto.request.ForumPublicationRequest;
import br.com.theroguedev.api.publication.dto.response.ForumPublicationResponse;
import br.com.theroguedev.api.publication.dto.response.ForumPublicationWithChildrenResponse;
import br.com.theroguedev.api.publication.entity.ForumPublication;
import br.com.theroguedev.api.publication.mapper.ForumPublicationMapper;
import br.com.theroguedev.api.publication.service.ForumPublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/forum/publication")
@RequiredArgsConstructor
public class ForumPublicationController implements ForumPublicationControllerDoc {

    private final ForumPublicationService forumPublicationService;
    private final ForumPublicationMapper forumPublicationMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('forum_publication:get_all')")
    public ResponseEntity<List<ForumPublicationResponse>> getAll() {
        return ResponseEntity.ok(forumPublicationService.findAll()
                .stream()
                .map(forumPublicationMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('forum_publication:get_by_id')")
    public ResponseEntity<ForumPublicationResponse> getById(@PathVariable UUID id) {
        return forumPublicationService.findById(id)
                .map(forumPublication -> ResponseEntity.ok(forumPublicationMapper.toResponse(forumPublication)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/children/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('forum_publication:get_by_id')")
    public ResponseEntity<ForumPublicationWithChildrenResponse> getByIdWithChildren(@PathVariable UUID id) {
        return forumPublicationService.findByIdWithChildren(id)
                .map(forumPublication -> ResponseEntity.ok(forumPublicationMapper.toResponseWithChildren(forumPublication)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('forum_publication:create')")
    public ResponseEntity<ForumPublicationResponse> save(@RequestBody @Valid ForumPublicationRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        ForumPublication newForumPublication = forumPublicationMapper.toForumPublication(request);
        ForumPublication savedForumPublication = forumPublicationService.save(newForumPublication, userData.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(forumPublicationMapper.toResponse(savedForumPublication));
    }

    @PatchMapping("activate/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('forum_publication:activate')")
    public ResponseEntity<ForumPublicationResponse> activate(@PathVariable UUID id) {
        ForumPublication forumPublication = forumPublicationService.changeStatus(id, true);

        return ResponseEntity.ok(forumPublicationMapper.toResponse(forumPublication));
    }

    @PatchMapping("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('forum_publication:deactivate')")
    public ResponseEntity<ForumPublicationResponse> deactivate(@PathVariable UUID id) {
        ForumPublication forumPublication = forumPublicationService.changeStatus(id, false);

        return ResponseEntity.ok(forumPublicationMapper.toResponse(forumPublication));
    }

}

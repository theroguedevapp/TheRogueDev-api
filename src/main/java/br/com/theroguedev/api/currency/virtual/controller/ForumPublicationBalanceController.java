package br.com.theroguedev.api.currency.virtual.controller;


import br.com.theroguedev.api.currency.virtual.dto.request.ForumPublicationBalanceRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.ForumPublicationBalanceResponse;
import br.com.theroguedev.api.currency.virtual.entity.ForumPublicationBalance;
import br.com.theroguedev.api.currency.virtual.mapper.ForumPublicationBalanceMapper;
import br.com.theroguedev.api.currency.virtual.service.ForumPublicationBalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/currency/virtual/forum/publication/balance")
@RequiredArgsConstructor
public class ForumPublicationBalanceController {

    private final ForumPublicationBalanceService forumPublicationBalanceService;
    private final ForumPublicationBalanceMapper forumPublicationBalanceMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:get_all')")
    public ResponseEntity<List<ForumPublicationBalanceResponse>> getAll() {
        return ResponseEntity.ok(forumPublicationBalanceService.findAll()
                .stream()
                .map(forumPublicationBalanceMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:get_by_id')")
    public ResponseEntity<ForumPublicationBalanceResponse> getById(@PathVariable UUID id) {
        return forumPublicationBalanceService.findById(id)
                .map(type -> ResponseEntity.ok(forumPublicationBalanceMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:create')")
    public ResponseEntity<ForumPublicationBalanceResponse> save(@RequestBody @Valid ForumPublicationBalanceRequest request) {
        ForumPublicationBalance newForumPublicationBalance = forumPublicationBalanceMapper.toForumPublicationBalance(request);
        ForumPublicationBalance savedForumPublicationBalance = forumPublicationBalanceService.save(newForumPublicationBalance);
        return ResponseEntity.status(HttpStatus.CREATED).body(forumPublicationBalanceMapper.toResponse(savedForumPublicationBalance));
    }

}

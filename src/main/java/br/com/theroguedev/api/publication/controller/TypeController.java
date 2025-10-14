package br.com.theroguedev.api.publication.controller;


import br.com.theroguedev.api.publication.controller.doc.TypeControllerDoc;
import br.com.theroguedev.api.publication.dto.request.TopicRequest;
import br.com.theroguedev.api.publication.dto.request.TypeRequest;
import br.com.theroguedev.api.publication.dto.response.TopicResponse;
import br.com.theroguedev.api.publication.dto.response.TypeResponse;
import br.com.theroguedev.api.publication.entity.Topic;
import br.com.theroguedev.api.publication.entity.Type;
import br.com.theroguedev.api.publication.mapper.TopicMapper;
import br.com.theroguedev.api.publication.mapper.TypeMapper;
import br.com.theroguedev.api.publication.service.TopicService;
import br.com.theroguedev.api.publication.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication/type")
@RequiredArgsConstructor
public class TypeController implements TypeControllerDoc {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:get_all')")
    public ResponseEntity<List<TypeResponse>> getAll() {
        return ResponseEntity.ok(typeService.findAll()
                .stream()
                .map(typeMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:get_by_id')")
    public ResponseEntity<TypeResponse> getById(@PathVariable Long id) {
        return typeService.findById(id)
                .map(type -> ResponseEntity.ok(typeMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:create')")
    public ResponseEntity<TypeResponse> save(@RequestBody @Valid TypeRequest request) {
        Type newType = typeMapper.toType(request);
        Type savedType = typeService.save(newType);
        return ResponseEntity.status(HttpStatus.CREATED).body(typeMapper.toResponse(savedType));
    }

    @PatchMapping("activate/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:activate')")
    public ResponseEntity<TypeResponse> activate(@PathVariable Long id) {
        Type type = typeService.changeStatus(id, true);

        return ResponseEntity.ok(typeMapper.toResponse(type));
    }

    @PatchMapping("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_type:deactivate')")
    public ResponseEntity<TypeResponse> deactivate(@PathVariable Long id) {
        Type type = typeService.changeStatus(id, false);

        return ResponseEntity.ok(typeMapper.toResponse(type));
    }

}

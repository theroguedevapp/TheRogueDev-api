package br.com.theroguedev.api.publication.controller;


import br.com.theroguedev.api.config.security.annotation.create.CanCreatePublicationType;
import br.com.theroguedev.api.config.security.annotation.read.CanReadPublicationType;
import br.com.theroguedev.api.config.security.annotation.update.CanUpdatePublicationType;
import br.com.theroguedev.api.publication.controller.doc.TypeControllerDoc;
import br.com.theroguedev.api.publication.dto.request.TypeRequest;
import br.com.theroguedev.api.publication.dto.response.TypeResponse;
import br.com.theroguedev.api.publication.entity.Type;
import br.com.theroguedev.api.publication.mapper.TypeMapper;
import br.com.theroguedev.api.publication.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication/type")
@RequiredArgsConstructor
public class TypeController implements TypeControllerDoc {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    @GetMapping
    @CanReadPublicationType
    public ResponseEntity<List<TypeResponse>> getAll() {
        return ResponseEntity.ok(typeService.findAll()
                .stream()
                .map(typeMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadPublicationType
    public ResponseEntity<TypeResponse> getById(@PathVariable Long id) {
        return typeService.findById(id)
                .map(type -> ResponseEntity.ok(typeMapper.toResponse(type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanCreatePublicationType
    public ResponseEntity<TypeResponse> save(@RequestBody @Valid TypeRequest request) {
        Type newType = typeMapper.toType(request);
        Type savedType = typeService.save(newType);
        return ResponseEntity.status(HttpStatus.CREATED).body(typeMapper.toResponse(savedType));
    }

    @PatchMapping("activate/{id}")
    @CanUpdatePublicationType
    public ResponseEntity<TypeResponse> activate(@PathVariable Long id) {
        Type type = typeService.changeStatus(id, true);

        return ResponseEntity.ok(typeMapper.toResponse(type));
    }

    @PatchMapping("deactivate/{id}")
    @CanUpdatePublicationType
    public ResponseEntity<TypeResponse> deactivate(@PathVariable Long id) {
        Type type = typeService.changeStatus(id, false);

        return ResponseEntity.ok(typeMapper.toResponse(type));
    }

}

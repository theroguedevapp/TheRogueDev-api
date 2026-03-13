package br.com.theroguedev.api.publication.controller;

import br.com.theroguedev.api.config.security.annotation.create.CanCreatePublicationStatus;
import br.com.theroguedev.api.config.security.annotation.read.CanReadPublicationStatus;
import br.com.theroguedev.api.config.security.annotation.update.CanUpdatePublicationStatus;
import br.com.theroguedev.api.publication.controller.doc.StatusControllerDoc;
import br.com.theroguedev.api.publication.dto.request.StatusRequest;
import br.com.theroguedev.api.publication.dto.response.StatusResponse;
import br.com.theroguedev.api.publication.entity.Status;
import br.com.theroguedev.api.publication.mapper.StatusMapper;
import br.com.theroguedev.api.publication.service.StatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication/status")
@RequiredArgsConstructor
public class StatusController implements StatusControllerDoc {

    private final StatusService statusService;
    private final StatusMapper statusMapper;

    @GetMapping
    @CanReadPublicationStatus
    public ResponseEntity<List<StatusResponse>> getAll() {
        return ResponseEntity.ok(statusService.findAll()
                .stream()
                .map(statusMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadPublicationStatus
    public ResponseEntity<StatusResponse> getById(@PathVariable Long id) {
        return statusService.findById(id)
                .map(status -> ResponseEntity.ok(statusMapper.toResponse(status)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanCreatePublicationStatus
    public ResponseEntity<StatusResponse> save(@RequestBody @Valid StatusRequest request) {
        Status newStatus = statusMapper.toStatus(request);
        Status savedStatus = statusService.save(newStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(statusMapper.toResponse(savedStatus));
    }

    @PatchMapping("activate/{id}")
    @CanUpdatePublicationStatus
    public ResponseEntity<StatusResponse> activate(@PathVariable Long id) {
        Status status = statusService.changeStatus(id, true);

        return ResponseEntity.ok(statusMapper.toResponse(status));
    }

    @PatchMapping("deactivate/{id}")
    @CanUpdatePublicationStatus
    public ResponseEntity<StatusResponse> deactivate(@PathVariable Long id) {

        Status status = statusService.changeStatus(id, false);

        return ResponseEntity.ok(statusMapper.toResponse(status));
    }

}

package br.com.theroguedev.api.publication.controller;


import br.com.theroguedev.api.config.security.annotation.create.CanCreatePublicationTool;
import br.com.theroguedev.api.config.security.annotation.read.CanReadPublicationTool;
import br.com.theroguedev.api.config.security.annotation.update.CanUpdatePublicationTool;
import br.com.theroguedev.api.publication.controller.doc.ToolControllerDoc;
import br.com.theroguedev.api.publication.dto.request.ToolRequest;
import br.com.theroguedev.api.publication.dto.response.ToolResponse;
import br.com.theroguedev.api.publication.entity.Tool;
import br.com.theroguedev.api.publication.mapper.ToolMapper;
import br.com.theroguedev.api.publication.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication/tool")
@RequiredArgsConstructor
public class ToolController implements ToolControllerDoc {

    private final ToolService toolService;
    private final ToolMapper toolMapper;

    @GetMapping
    @CanReadPublicationTool
    public ResponseEntity<List<ToolResponse>> getAll() {
        return ResponseEntity.ok(toolService.findAll()
                .stream()
                .map(toolMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadPublicationTool
    public ResponseEntity<ToolResponse> getById(@PathVariable Long id) {
        return toolService.findById(id)
                .map(tool -> ResponseEntity.ok(toolMapper.toResponse(tool)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @CanCreatePublicationTool
    public ResponseEntity<ToolResponse> save(@RequestBody @Valid ToolRequest request) {
        Tool newTool = toolMapper.toTool(request);
        Tool savedTool = toolService.save(newTool);
        return ResponseEntity.status(HttpStatus.CREATED).body(toolMapper.toResponse(savedTool));
    }

    @PatchMapping("activate/{id}")
    @CanUpdatePublicationTool
    public ResponseEntity<ToolResponse> activate(@PathVariable Long id) {
        Tool tool = toolService.changeStatus(id, true);

        return ResponseEntity.ok(toolMapper.toResponse(tool));
    }

    @PatchMapping("deactivate/{id}")
    @CanUpdatePublicationTool
    public ResponseEntity<ToolResponse> deactivate(@PathVariable Long id) {
        Tool tool = toolService.changeStatus(id, false);

        return ResponseEntity.ok(toolMapper.toResponse(tool));
    }

}

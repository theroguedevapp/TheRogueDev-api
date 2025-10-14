package br.com.theroguedev.api.publication.controller;


import br.com.theroguedev.api.publication.controller.doc.TopicControllerDoc;
import br.com.theroguedev.api.publication.dto.request.ToolRequest;
import br.com.theroguedev.api.publication.dto.request.TopicRequest;
import br.com.theroguedev.api.publication.dto.response.ToolResponse;
import br.com.theroguedev.api.publication.dto.response.TopicResponse;
import br.com.theroguedev.api.publication.entity.Tool;
import br.com.theroguedev.api.publication.entity.Topic;
import br.com.theroguedev.api.publication.mapper.ToolMapper;
import br.com.theroguedev.api.publication.mapper.TopicMapper;
import br.com.theroguedev.api.publication.service.ToolService;
import br.com.theroguedev.api.publication.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publication/topic")
@RequiredArgsConstructor
public class TopicController implements TopicControllerDoc {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_topic:get_all')")
    public ResponseEntity<List<TopicResponse>> getAll() {
        return ResponseEntity.ok(topicService.findAll()
                .stream()
                .map(topicMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_topic:get_by_id')")
    public ResponseEntity<TopicResponse> getById(@PathVariable Long id) {
        return topicService.findById(id)
                .map(topic -> ResponseEntity.ok(topicMapper.toResponse(topic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_topic:create')")
    public ResponseEntity<TopicResponse> save(@RequestBody @Valid TopicRequest request) {
        Topic newTopic = topicMapper.toTopic(request);
        Topic savedTopic = topicService.save(newTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicMapper.toResponse(savedTopic));
    }

    @PatchMapping("activate/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_topic:activate')")
    public ResponseEntity<TopicResponse> activate(@PathVariable Long id) {
        Topic topic = topicService.changeStatus(id, true);

        return ResponseEntity.ok(topicMapper.toResponse(topic));
    }

    @PatchMapping("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('publication_topic:deactivate')")
    public ResponseEntity<TopicResponse> deactivate(@PathVariable Long id) {
        Topic topic = topicService.changeStatus(id, false);

        return ResponseEntity.ok(topicMapper.toResponse(topic));
    }

}

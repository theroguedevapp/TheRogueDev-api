package br.com.theroguedev.api.user.controller;


import br.com.theroguedev.api.config.security.annotation.read.CanReadPermission;
import br.com.theroguedev.api.user.controller.doc.PermissionControllerDoc;
import br.com.theroguedev.api.user.dto.response.PermissionResponse;
import br.com.theroguedev.api.user.mapper.PermissionMapper;
import br.com.theroguedev.api.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/permission")
@RequiredArgsConstructor
public class PermissionController implements PermissionControllerDoc {

    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;

    @GetMapping
    @CanReadPermission
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(permissionService.findAll()
                .stream()
                .map(permissionMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadPermission
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return permissionService.findById(id)
                .map(permission -> ResponseEntity.ok(permissionMapper.toResponse(permission)))
                .orElse(ResponseEntity.notFound().build());
    }
}

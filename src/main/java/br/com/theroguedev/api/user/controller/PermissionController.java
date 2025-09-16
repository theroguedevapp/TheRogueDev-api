package br.com.theroguedev.api.user.controller;


import br.com.theroguedev.api.user.dto.request.PermissionRequest;
import br.com.theroguedev.api.user.dto.response.PermissionResponse;
import br.com.theroguedev.api.user.entity.Permission;
import br.com.theroguedev.api.user.mapper.PermissionMapper;
import br.com.theroguedev.api.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;
    private final PermissionMapper permissionMapper;


    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(permissionService.findAll()
                .stream()
                .map(permissionMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return permissionService.findById(id)
                .map(permission -> ResponseEntity.ok(permissionMapper.toResponse(permission)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> save(@RequestBody PermissionRequest request) {
        Permission newPermission = permissionMapper.toPermission(request);
        Permission savedPermission = permissionService.save(newPermission);
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionMapper.toResponse(savedPermission));
    }

}

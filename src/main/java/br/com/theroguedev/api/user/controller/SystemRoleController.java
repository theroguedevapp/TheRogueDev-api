package br.com.theroguedev.api.user.controller;


import br.com.theroguedev.api.config.security.annotation.read.CanReadSystemRole;
import br.com.theroguedev.api.user.controller.doc.SystemRoleControllerDoc;
import br.com.theroguedev.api.user.dto.request.ChangePermissionsRequest;
import br.com.theroguedev.api.user.dto.response.SystemRoleResponse;
import br.com.theroguedev.api.user.mapper.SystemRoleMapper;
import br.com.theroguedev.api.user.service.SystemRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/system-role")
@RequiredArgsConstructor
public class SystemRoleController implements SystemRoleControllerDoc {

    private final SystemRoleService systemRoleService;
    private final SystemRoleMapper systemRoleMapper;


    @GetMapping
    @CanReadSystemRole
    public ResponseEntity<List<SystemRoleResponse>> getAll() {
        return ResponseEntity.ok(systemRoleService.findAll()
                .stream()
                .map(systemRoleMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    @CanReadSystemRole
    public ResponseEntity<SystemRoleResponse> getById(@PathVariable Long id) {
        return systemRoleService.findById(id)
                .map(role -> ResponseEntity.ok(systemRoleMapper.toResponse(role)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/permissions/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_admin:all'")
    public ResponseEntity<SystemRoleResponse> changePermissions(@PathVariable Long id, @RequestBody ChangePermissionsRequest request) {
        return systemRoleService.changePermissions(id, request.permissions())
                .map(systemRole -> ResponseEntity.ok(systemRoleMapper.toResponse(systemRole)))
                .orElse(ResponseEntity.notFound().build());
    }
}

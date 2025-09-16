package br.com.theroguedev.api.user.controller;


import br.com.theroguedev.api.user.dto.request.SystemRoleRequest;
import br.com.theroguedev.api.user.dto.response.SystemRoleResponse;
import br.com.theroguedev.api.user.entity.SystemRole;
import br.com.theroguedev.api.user.mapper.SystemRoleMapper;
import br.com.theroguedev.api.user.service.SystemRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/system-role")
@RequiredArgsConstructor
public class SystemRoleController {

    private final SystemRoleService systemRoleService;
    private final SystemRoleMapper systemRoleMapper;


    @GetMapping
    public ResponseEntity<List<SystemRoleResponse>> getAll() {
        return ResponseEntity.ok(systemRoleService.findAll()
                .stream()
                .map(systemRoleMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemRoleResponse> getById(@PathVariable Long id) {
        return systemRoleService.findById(id)
                .map(role -> ResponseEntity.ok(systemRoleMapper.toResponse(role)))
                .orElse(ResponseEntity.notFound().build());
    }

}

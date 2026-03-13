package br.com.theroguedev.api.user.controller.doc;

import br.com.theroguedev.api.user.dto.request.ChangePermissionsRequest;
import br.com.theroguedev.api.user.dto.response.SystemRoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User System Role", description = "System role management operations")
public interface SystemRoleControllerDoc {

    @Operation(
            summary = "Get all system roles",
            description = "Retrieve all available system roles"
    )
    @ApiResponse(
            responseCode = "200",
            description = "System roles retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SystemRoleResponse.class)))
    )
    ResponseEntity<List<SystemRoleResponse>> getAll();

    @Operation(
            summary = "Get system role by ID",
            description = "Retrieve a specific system role by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "System role found",
            content = @Content(schema = @Schema(implementation = SystemRoleResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "System role not found"
    )
    ResponseEntity<SystemRoleResponse> getById(@Parameter(description = "System Role ID") Long id);

    @Operation(
            summary = "Update role permissions", 
            description = "Update the permissions assigned to a system role"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Permissions updated successfully",
            content = @Content(schema = @Schema(implementation = SystemRoleResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "System role not found"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid permissions data"
    )
    ResponseEntity<SystemRoleResponse> changePermissions(@Parameter(description = "System Role ID") Long id, ChangePermissionsRequest request);
}
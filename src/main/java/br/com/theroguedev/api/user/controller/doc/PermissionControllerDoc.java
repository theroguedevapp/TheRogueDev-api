package br.com.theroguedev.api.user.controller.doc;

import br.com.theroguedev.api.user.dto.request.PermissionRequest;
import br.com.theroguedev.api.user.dto.response.PermissionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User Permission", description = "User permission management operations")
public interface PermissionControllerDoc {

    @Operation(
            summary = "Get all permissions",
            description = "Retrieve all available permissions"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Permissions retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PermissionResponse.class)))
    )
    ResponseEntity<List<PermissionResponse>> getAll();

    @Operation(
            summary = "Get permission by ID",
            description = "Retrieve a specific permission by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Permission found",
            content = @Content(schema = @Schema(implementation = PermissionResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Permission not found"
    )
    ResponseEntity<PermissionResponse> getById(@Parameter(description = "Permission ID") Long id);

    @Operation(
            summary = "Create permission",
            description = "Create a new permission"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Permission created successfully",
            content = @Content(schema = @Schema(implementation = PermissionResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid permission data"
    )
    ResponseEntity<PermissionResponse> save(PermissionRequest request);
}
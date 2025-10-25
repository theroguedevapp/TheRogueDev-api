package br.com.theroguedev.api.user.controller.doc;

import br.com.theroguedev.api.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "User", description = "User management operations")
public interface UserControllerDoc {

    @Operation(
            summary = "Get all users",
            description = "Retrieve all registered users"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))
    )
    ResponseEntity<List<UserResponse>> getAll();

    @Operation(
            summary = "Get user by ID",
            description = "Retrieve a specific user by their ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found"
    )
    ResponseEntity<UserResponse> getById(@Parameter(description = "User ID") UUID id);

    @Operation(
            summary = "Get user by username",
            description = "Retrieve a specific user by their username"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found"
    )
    ResponseEntity<UserResponse> getByUsername(@Parameter(description = "Username") String username);
}
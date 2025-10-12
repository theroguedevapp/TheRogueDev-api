package br.com.theroguedev.api.user.controller.doc;

import br.com.theroguedev.api.user.dto.response.UserProfileResponse;
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

@Tag(name = "User Profile", description = "User profile management operations")
public interface UserProfileControllerDoc {

    @Operation(
            summary = "Get all user profiles",
            description = "Retrieve all user profiles"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User profiles retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserProfileResponse.class)))
    )
    ResponseEntity<List<UserProfileResponse>> getAll();

    @Operation(
            summary = "Get user profile by ID",
            description = "Retrieve a specific user profile by user ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User profile found",
            content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "User profile not found"
    )
    ResponseEntity<UserProfileResponse> getById(@Parameter(description = "User ID") UUID id);

    @Operation(
            summary = "Get user profile by username",
            description = "Retrieve a specific user profile by username"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User profile found",
            content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "User profile not found"
    )
    ResponseEntity<UserProfileResponse> getByUsername(@Parameter(description = "Username") String username);
}
package br.com.theroguedev.api.publication.controller.doc;

import br.com.theroguedev.api.publication.dto.request.ForumPublicationRequest;
import br.com.theroguedev.api.publication.dto.response.ForumPublicationResponse;
import br.com.theroguedev.api.publication.dto.response.ForumPublicationWithChildrenResponse;
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

@Tag(name = "Forum Publication", description = "Forum publication management operations")
public interface ForumPublicationControllerDoc {

    @Operation(
            summary = "Get all forum publications",
            description = "Retrieve all forum publications"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publications retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ForumPublicationResponse.class)))
    )
    ResponseEntity<List<ForumPublicationResponse>> getAll();

    @Operation(
            summary = "Get forum publication by ID",
            description = "Retrieve a specific forum publication by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publication found",
            content = @Content(schema = @Schema(implementation = ForumPublicationResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication not found"
    )
    ResponseEntity<ForumPublicationResponse> getById(@Parameter(description = "Forum Publication ID") UUID id);

    @Operation(
            summary = "Get forum publication with children by ID",
            description = "Retrieve a specific forum publication with its children by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publication with children found",
            content = @Content(schema = @Schema(implementation = ForumPublicationWithChildrenResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication not found"
    )
    ResponseEntity<ForumPublicationWithChildrenResponse> getByIdWithChildren(@Parameter(description = "Forum Publication ID") UUID id);

    @Operation(
            summary = "Create forum publication",
            description = "Create a new forum publication"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Forum publication created successfully",
            content = @Content(schema = @Schema(implementation = ForumPublicationResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid forum publication data"
    )
    ResponseEntity<ForumPublicationResponse> save(ForumPublicationRequest request);

    @Operation(
            summary = "Activate forum publication",
            description = "Activate a forum publication by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publication activated successfully",
            content = @Content(schema = @Schema(implementation = ForumPublicationResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication not found"
    )
    ResponseEntity<ForumPublicationResponse> activate(@Parameter(description = "Forum Publication ID") UUID id);

    @Operation(
            summary = "Deactivate forum publication",
            description = "Deactivate a forum publication by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publication deactivated successfully",
            content = @Content(schema = @Schema(implementation = ForumPublicationResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication not found"
    )
    ResponseEntity<ForumPublicationResponse> deactivate(@Parameter(description = "Forum Publication ID") UUID id);
}
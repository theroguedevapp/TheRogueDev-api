package br.com.theroguedev.api.publication.controller.doc;

import br.com.theroguedev.api.publication.dto.request.TopicRequest;
import br.com.theroguedev.api.publication.dto.response.TopicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Publication Topic", description = "Publication topic management operations")
public interface TopicControllerDoc {

    @Operation(
            summary = "Get all publication topics",
            description = "Retrieve all available publication topics"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication topics retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TopicResponse.class)))
    )
    ResponseEntity<List<TopicResponse>> getAll();

    @Operation(
            summary = "Get publication topic by ID",
            description = "Retrieve a specific publication topic by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication topic found",
            content = @Content(schema = @Schema(implementation = TopicResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication topic not found"
    )
    ResponseEntity<TopicResponse> getById(@Parameter(description = "Topic ID") Long id);

    @Operation(
            summary = "Create publication topic",
            description = "Create a new publication topic"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Publication topic created successfully",
            content = @Content(schema = @Schema(implementation = TopicResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid topic data"
    )
    ResponseEntity<TopicResponse> save(TopicRequest request);

    @Operation(
            summary = "Activate publication topic",
            description = "Activate a publication topic by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication topic activated successfully",
            content = @Content(schema = @Schema(implementation = TopicResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication topic not found"
    )
    ResponseEntity<TopicResponse> activate(@Parameter(description = "Topic ID") Long id);

    @Operation(
            summary = "Deactivate publication topic",
            description = "Deactivate a publication topic by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication topic deactivated successfully",
            content = @Content(schema = @Schema(implementation = TopicResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication topic not found"
    )
    ResponseEntity<TopicResponse> deactivate(@Parameter(description = "Topic ID") Long id);
}
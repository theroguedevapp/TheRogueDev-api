package br.com.theroguedev.api.publication.controller.doc;

import br.com.theroguedev.api.publication.dto.request.ToolRequest;
import br.com.theroguedev.api.publication.dto.response.ToolResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Publication Tool", description = "Publication tool management operations")
public interface ToolControllerDoc {

    @Operation(
            summary = "Get all publication tools",
            description = "Retrieve all available publication tools"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication tools retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ToolResponse.class)))
    )
    ResponseEntity<List<ToolResponse>> getAll();

    @Operation(
            summary = "Get publication tool by ID",
            description = "Retrieve a specific publication tool by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication tool found",
            content = @Content(schema = @Schema(implementation = ToolResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication tool not found"
    )
    ResponseEntity<ToolResponse> getById(@Parameter(description = "Tool ID") Long id);

    @Operation(
            summary = "Create publication tool",
            description = "Create a new publication tool"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Publication tool created successfully",
            content = @Content(schema = @Schema(implementation = ToolResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid tool data"
    )
    ResponseEntity<ToolResponse> save(ToolRequest request);

    @Operation(
            summary = "Activate publication tool",
            description = "Activate a publication tool by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication tool activated successfully",
            content = @Content(schema = @Schema(implementation = ToolResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication tool not found"
    )
    ResponseEntity<ToolResponse> activate(@Parameter(description = "Tool ID") Long id);

    @Operation(
            summary = "Deactivate publication tool",
            description = "Deactivate a publication tool by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication tool deactivated successfully",
            content = @Content(schema = @Schema(implementation = ToolResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication tool not found"
    )
    ResponseEntity<ToolResponse> deactivate(@Parameter(description = "Tool ID") Long id);
}
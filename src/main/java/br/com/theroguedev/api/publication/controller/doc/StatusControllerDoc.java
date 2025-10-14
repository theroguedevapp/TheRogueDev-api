package br.com.theroguedev.api.publication.controller.doc;

import br.com.theroguedev.api.publication.dto.request.StatusRequest;
import br.com.theroguedev.api.publication.dto.response.StatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Publication Status", description = "Publication status management operations")
public interface StatusControllerDoc {

    @Operation(
            summary = "Get all publication statuses",
            description = "Retrieve all available publication statuses"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication statuses retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StatusResponse.class)))
    )
    ResponseEntity<List<StatusResponse>> getAll();

    @Operation(
            summary = "Get publication status by ID",
            description = "Retrieve a specific publication status by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication status found",
            content = @Content(schema = @Schema(implementation = StatusResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication status not found"
    )
    ResponseEntity<StatusResponse> getById(@Parameter(description = "Status ID") Long id);

    @Operation(
            summary = "Create publication status",
            description = "Create a new publication status"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Publication status created successfully",
            content = @Content(schema = @Schema(implementation = StatusResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid status data"
    )
    ResponseEntity<StatusResponse> save(StatusRequest request);

    @Operation(
            summary = "Activate publication status",
            description = "Activate a publication status by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication status activated successfully",
            content = @Content(schema = @Schema(implementation = StatusResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication status not found"
    )
    ResponseEntity<StatusResponse> activate(@Parameter(description = "Status ID") Long id);

    @Operation(
            summary = "Deactivate publication status",
            description = "Deactivate a publication status by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication status deactivated successfully",
            content = @Content(schema = @Schema(implementation = StatusResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication status not found"
    )
    ResponseEntity<StatusResponse> deactivate(@Parameter(description = "Status ID") Long id);
}
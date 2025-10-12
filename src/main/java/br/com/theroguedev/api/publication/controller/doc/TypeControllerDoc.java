package br.com.theroguedev.api.publication.controller.doc;

import br.com.theroguedev.api.publication.dto.request.TypeRequest;
import br.com.theroguedev.api.publication.dto.response.TypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Publication Type", description = "Publication type management operations")
public interface TypeControllerDoc {

    @Operation(
            summary = "Get all publication types",
            description = "Retrieve all available publication types"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication types retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TypeResponse.class)))
    )
    ResponseEntity<List<TypeResponse>> getAll();

    @Operation(
            summary = "Get publication type by ID",
            description = "Retrieve a specific publication type by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication type found",
            content = @Content(schema = @Schema(implementation = TypeResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication type not found"
    )
    ResponseEntity<TypeResponse> getById(@Parameter(description = "Type ID") Long id);

    @Operation(
            summary = "Create publication type",
            description = "Create a new publication type"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Publication type created successfully",
            content = @Content(schema = @Schema(implementation = TypeResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid type data"
    )
    ResponseEntity<TypeResponse> save(TypeRequest request);

    @Operation(
            summary = "Activate publication type",
            description = "Activate a publication type by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication type activated successfully",
            content = @Content(schema = @Schema(implementation = TypeResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication type not found"
    )
    ResponseEntity<TypeResponse> activate(@Parameter(description = "Type ID") Long id);

    @Operation(
            summary = "Deactivate publication type",
            description = "Deactivate a publication type by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Publication type deactivated successfully",
            content = @Content(schema = @Schema(implementation = TypeResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Publication type not found"
    )
    ResponseEntity<TypeResponse> deactivate(@Parameter(description = "Type ID") Long id);
}
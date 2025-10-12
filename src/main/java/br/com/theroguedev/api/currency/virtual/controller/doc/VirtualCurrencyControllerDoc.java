package br.com.theroguedev.api.currency.virtual.controller.doc;

import br.com.theroguedev.api.currency.virtual.dto.request.VirtualCurrencyRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.VirtualCurrencyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Currency Virtual VirtualCurrency", description = "Virtual currency management operations")
public interface VirtualCurrencyControllerDoc {

    @Operation(
            summary = "Get all virtual currencies",
            description = "Retrieve all available virtual currencies"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Virtual currencies retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VirtualCurrencyResponse.class)))
    )
    ResponseEntity<List<VirtualCurrencyResponse>> getAll();

    @Operation(
            summary = "Get virtual currency by ID",
            description = "Retrieve a specific virtual currency by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Virtual currency found",
            content = @Content(schema = @Schema(implementation = VirtualCurrencyResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Virtual currency not found"
    )
    ResponseEntity<VirtualCurrencyResponse> getById(@Parameter(description = "Virtual Currency ID") Long id);

    @Operation(
            summary = "Create virtual currency",
            description = "Create a new virtual currency"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Virtual currency created successfully",
            content = @Content(schema = @Schema(implementation = VirtualCurrencyResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid virtual currency data"
    )
    ResponseEntity<VirtualCurrencyResponse> save(VirtualCurrencyRequest request);
}
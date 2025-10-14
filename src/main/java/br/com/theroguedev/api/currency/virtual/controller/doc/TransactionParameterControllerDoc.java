package br.com.theroguedev.api.currency.virtual.controller.doc;

import br.com.theroguedev.api.currency.virtual.dto.request.TransactionParameterRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionParameterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Currency Virtual TransactionParameters", description = "Transaction parameter management operations")
public interface TransactionParameterControllerDoc {

    @Operation(
            summary = "Get all transaction parameters",
            description = "Retrieve all transaction parameters"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transaction parameters retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionParameterResponse.class)))
    )
    ResponseEntity<List<TransactionParameterResponse>> getAll();

    @Operation(
            summary = "Get transaction parameter by ID",
            description = "Retrieve a specific transaction parameter by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transaction parameter found",
            content = @Content(schema = @Schema(implementation = TransactionParameterResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Transaction parameter not found"
    )
    ResponseEntity<TransactionParameterResponse> getById(@Parameter(description = "Transaction Parameter ID") Long id);

    @Operation(
            summary = "Create transaction parameter",
            description = "Create a new transaction parameter"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Transaction parameter created successfully",
            content = @Content(schema = @Schema(implementation = TransactionParameterResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid transaction parameter data"
    )
    ResponseEntity<TransactionParameterResponse> save(TransactionParameterRequest request);
}
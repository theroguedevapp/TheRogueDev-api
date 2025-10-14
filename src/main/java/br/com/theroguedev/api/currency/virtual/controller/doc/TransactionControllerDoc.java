package br.com.theroguedev.api.currency.virtual.controller.doc;

import br.com.theroguedev.api.currency.virtual.dto.request.TransactionRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "Currency Virtual Transaction", description = "Virtual currency transaction management operations")
public interface TransactionControllerDoc {

    @Operation(
            summary = "Get all transactions",
            description = "Retrieve all virtual currency transactions"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transactions retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class)))
    )
    ResponseEntity<List<TransactionResponse>> getAll();

    @Operation(
            summary = "Get transaction by ID",
            description = "Retrieve a specific transaction by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transaction found",
            content = @Content(schema = @Schema(implementation = TransactionResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Transaction not found"
    )
    ResponseEntity<TransactionResponse> getById(@Parameter(description = "Transaction ID") UUID id);

    @Operation(
            summary = "Create transaction",
            description = "Create a new virtual currency transaction"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Transaction created successfully",
            content = @Content(schema = @Schema(implementation = TransactionResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid transaction data"
    )
    ResponseEntity<TransactionResponse> save(TransactionRequest request);
}

package br.com.theroguedev.api.currency.virtual.controller.doc;

import br.com.theroguedev.api.currency.virtual.dto.request.TransactionTypeRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.TransactionTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Currency Virtual TransactionType", description = "Virtual currency transaction type management operations")
public interface TransactionTypeControllerDoc {

    @Operation(
            summary = "Get all transaction types",
            description = "Retrieve all virtual currency transaction types"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transaction types retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionTypeResponse.class)))
    )
    ResponseEntity<List<TransactionTypeResponse>> getAll();

    @Operation(
            summary = "Get transaction type by ID",
            description = "Retrieve a specific transaction type by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Transaction type found",
            content = @Content(schema = @Schema(implementation = TransactionTypeResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Transaction type not found"
    )
    ResponseEntity<TransactionTypeResponse> getById(@Parameter(description = "Transaction Type ID") Long id);

    @Operation(
            summary = "Create transaction type",
            description = "Create a new virtual currency transaction type"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Transaction type created successfully",
            content = @Content(schema = @Schema(implementation = TransactionTypeResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid transaction type data"
    )
    ResponseEntity<TransactionTypeResponse> save(TransactionTypeRequest request);
}
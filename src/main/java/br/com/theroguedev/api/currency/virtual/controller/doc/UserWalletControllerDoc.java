package br.com.theroguedev.api.currency.virtual.controller.doc;

import br.com.theroguedev.api.currency.virtual.dto.request.UserWalletRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.UserWalletResponse;
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

@Tag(name = "Currency Virtual UserWallet", description = "User wallet management operations")
public interface UserWalletControllerDoc {

    @Operation(
            summary = "Get all user wallets",
            description = "Retrieve all user wallets"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User wallets retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserWalletResponse.class)))
    )
    ResponseEntity<List<UserWalletResponse>> getAll();

    @Operation(
            summary = "Get user wallet by ID",
            description = "Retrieve a specific user wallet by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User wallet found",
            content = @Content(schema = @Schema(implementation = UserWalletResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "User wallet not found"
    )
    ResponseEntity<UserWalletResponse> getById(@Parameter(description = "User Wallet ID") UUID id);

    @Operation(
            summary = "Create user wallet",
            description = "Create a new user wallet"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User wallet created successfully",
            content = @Content(schema = @Schema(implementation = UserWalletResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid user wallet data"
    )
    ResponseEntity<UserWalletResponse> save(UserWalletRequest request);
}
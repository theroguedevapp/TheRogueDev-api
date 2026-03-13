package br.com.theroguedev.api.currency.virtual.controller.doc;

import br.com.theroguedev.api.currency.virtual.dto.request.ForumPublicationBalanceRequest;
import br.com.theroguedev.api.currency.virtual.dto.request.ForumPublicationBalanceVoteRequest;
import br.com.theroguedev.api.currency.virtual.dto.response.ForumPublicationBalanceResponse;
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

@Tag(name = "Currency Virtual ForumPublicationBalance", description = "Forum publication balance management operations")
public interface ForumPublicationBalanceControllerDoc {

    @Operation(
            summary = "Get all forum publication balances",
            description = "Retrieve all forum publication balances"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publication balances retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ForumPublicationBalanceResponse.class)))
    )
    ResponseEntity<List<ForumPublicationBalanceResponse>> getAll();

    @Operation(
            summary = "Get forum publication balance by ID",
            description = "Retrieve a specific forum publication balance by publication ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Forum publication balance found",
            content = @Content(schema = @Schema(implementation = ForumPublicationBalanceResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication balance not found"
    )
    ResponseEntity<ForumPublicationBalanceResponse> getById(@Parameter(description = "Forum Publication ID") UUID id);

    @Operation(
            summary = "Create forum publication balance",
            description = "Create a new forum publication balance"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Forum publication balance created successfully",
            content = @Content(schema = @Schema(implementation = ForumPublicationBalanceResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid forum publication balance data"
    )
    ResponseEntity<ForumPublicationBalanceResponse> save(ForumPublicationBalanceRequest request);

    @Operation(
            summary = "Upvote forum publication",
            description = "Add an upvote to a forum publication"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Upvote added successfully",
            content = @Content(schema = @Schema(implementation = ForumPublicationBalanceResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication not found"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid vote request"
    )
    ResponseEntity<ForumPublicationBalanceResponse> upvote(ForumPublicationBalanceVoteRequest request);

    @Operation(
            summary = "Downvote forum publication",
            description = "Add a downvote to a forum publication"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Downvote added successfully",
            content = @Content(schema = @Schema(implementation = ForumPublicationBalanceResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Forum publication not found"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid vote request"
    )
    ResponseEntity<ForumPublicationBalanceResponse> downvote(ForumPublicationBalanceVoteRequest request);
}
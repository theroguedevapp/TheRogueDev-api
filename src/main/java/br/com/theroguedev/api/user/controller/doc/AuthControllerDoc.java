package br.com.theroguedev.api.user.controller.doc;

import br.com.theroguedev.api.user.dto.request.LoginRequest;
import br.com.theroguedev.api.user.dto.request.UserRequest;
import br.com.theroguedev.api.user.dto.response.AuthenticatedUserResponse;
import br.com.theroguedev.api.user.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Authentication operations")
public interface AuthControllerDoc {

    @Operation(
            summary = "User login",
            description = "Authenticate user with email and password"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials"
    )
    ResponseEntity<LoginResponse> login(LoginRequest request);

    @Operation(
            summary = "User registration",
            description = "Register a new user account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = AuthenticatedUserResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid user data"
    )
    ResponseEntity<AuthenticatedUserResponse> save(UserRequest request);

    @Operation(
            summary = "Validate token",
            description = "Validate the current authentication token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token is valid"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Token is invalid or expired"
    )
    ResponseEntity<?> validateToken();

    @Operation(
            summary = "User logout",
            description = "Logout the current user and invalidate session"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Logout successful"
    )
    ResponseEntity<Void> logout();
}
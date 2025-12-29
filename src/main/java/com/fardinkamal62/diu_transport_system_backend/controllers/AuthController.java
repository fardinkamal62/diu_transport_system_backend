package com.fardinkamal62.diu_transport_system_backend.controllers;

import com.fardinkamal62.diu_transport_system_backend.dtos.AdminLoginRequestDto;
import com.fardinkamal62.diu_transport_system_backend.dtos.AuthResponseDto;
import com.fardinkamal62.diu_transport_system_backend.services.AuthService;
import com.fardinkamal62.diu_transport_system_backend.dtos.ErrorResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthResponseDto> adminLogin(@Valid @RequestBody AdminLoginRequestDto loginRequest) {
        if (!loginRequest.hasValidIdentifier()) {
            throw new IllegalArgumentException("Either email or username must be provided");
        }

        AuthResponseDto authResponse = authService.adminLogin(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message("Invalid email or password")
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(HttpClientErrorException.BadRequest ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message("Bad request: " + ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
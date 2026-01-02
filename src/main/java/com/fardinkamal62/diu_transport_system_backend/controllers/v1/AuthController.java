package com.fardinkamal62.diu_transport_system_backend.controllers.v1;

import com.fardinkamal62.diu_transport_system_backend.dtos.AdminLoginRequestDto;
import com.fardinkamal62.diu_transport_system_backend.dtos.AuthResponseDto;
import com.fardinkamal62.diu_transport_system_backend.services.AuthService;
import com.fardinkamal62.diu_transport_system_backend.dtos.ErrorResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthResponseDto> adminLogin(@Valid @RequestBody AdminLoginRequestDto loginRequest) {
        if (!loginRequest.hasValidIdentifier()) {
            logger.warn("Admin login failed - No valid identifier provided");
            throw new IllegalArgumentException("Either email or username must be provided");
        }

        AuthResponseDto authResponse = authService.adminLogin(loginRequest);
        logger.info("Admin login successful - Username: {}", authResponse.getUsername());
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
        logger.warn("IllegalArgumentException caught: {}", ex.getMessage());
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(HttpClientErrorException.BadRequest ex) {
        logger.error("BadRequest exception caught: {}", ex.getMessage());
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message("Bad request: " + ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
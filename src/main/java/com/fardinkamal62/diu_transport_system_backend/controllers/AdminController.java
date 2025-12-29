package com.fardinkamal62.diu_transport_system_backend.controllers;

import com.fardinkamal62.diu_transport_system_backend.dtos.AddDriverDtoRequest;
import com.fardinkamal62.diu_transport_system_backend.dtos.AddDriverResponseDto;
import com.fardinkamal62.diu_transport_system_backend.dtos.ErrorResponseDto;
import com.fardinkamal62.diu_transport_system_backend.services.DriverService;
import com.fardinkamal62.diu_transport_system_backend.dtos.ApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final DriverService driverService;

    @Autowired
    public AdminController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/driver")
    public ApiResponseDto<AddDriverResponseDto> addDriver(@RequestBody AddDriverDtoRequest addDriverDtoRequest) {
        AddDriverResponseDto driver = driverService.addDriver(addDriverDtoRequest);
        return ApiResponseDto.success(driver);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message("An unexpected error occurred: " + ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

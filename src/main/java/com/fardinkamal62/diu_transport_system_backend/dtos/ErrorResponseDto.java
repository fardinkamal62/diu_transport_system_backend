package com.fardinkamal62.diu_transport_system_backend.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private String message;
    private String error;
    private int status;
    private String path;
    private LocalDateTime timestamp;
    private List<ValidationError> validationErrors;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
        private Object rejectedValue;
    }

    public static ErrorResponseDto of(String message, int status, String path) {
        return ErrorResponseDto.builder()
                .message(message)
                .status(status)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponseDto of(String message, String error, int status, String path) {
        return ErrorResponseDto.builder()
                .message(message)
                .error(error)
                .status(status)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}

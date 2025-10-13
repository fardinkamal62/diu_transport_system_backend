package com.fardinkamal62.diu_transport_system_backend.dtos;

import lombok.Data;

@Data
public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponseDto<T> success(T data) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage("Operation successful");
        response.setData(data);
        return response;
    }

    public static <T> ApiResponseDto<T> success(String message, T data) {
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
package com.fardinkamal62.diu_transport_system_backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDto {
    private String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

}

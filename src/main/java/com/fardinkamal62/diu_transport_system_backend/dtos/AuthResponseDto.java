package com.fardinkamal62.diu_transport_system_backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseDto {
    private String token;

    public AuthResponseDto(String token) {
        this.token = token;
    }
}

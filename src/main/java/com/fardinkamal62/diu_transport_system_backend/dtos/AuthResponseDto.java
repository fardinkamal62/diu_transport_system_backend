package com.fardinkamal62.diu_transport_system_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";

    private Long expiresIn; // in seconds
    private String username;
    private String role;
    private LocalDateTime issuedAt;
}

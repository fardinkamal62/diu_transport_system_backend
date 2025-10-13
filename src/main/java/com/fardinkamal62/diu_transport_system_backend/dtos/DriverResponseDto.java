package com.fardinkamal62.diu_transport_system_backend.dtos;

import lombok.Data;

@Data
public class DriverResponseDto {
    private Long id;
    private String full_name;
    private String phoneNumber;
    private DriverStatus status;

    public enum DriverStatus {
        active, inactive
    }
}

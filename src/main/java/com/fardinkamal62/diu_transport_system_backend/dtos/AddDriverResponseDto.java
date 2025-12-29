package com.fardinkamal62.diu_transport_system_backend.dtos;

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
public class AddDriverResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private DriverStatus status;
    private List<VehicleType> preferredVehicles;
    private LocalDateTime createdAt;

    public enum DriverStatus {
        ACTIVE, INACTIVE
    }

    public enum VehicleType {
        BUS, MICROBUS
    }
}

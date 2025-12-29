package com.fardinkamal62.diu_transport_system_backend.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDriverDtoRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid")
    private String phoneNumber;

    @NotNull(message = "Preferred vehicles are required")
    @Size(min = 1, message = "At least one preferred vehicle must be specified")
    private List<VehicleType> preferredVehicles;

    @NotNull(message = "Status is required")
    private Status status = Status.INACTIVE;

    public enum Status {
        ACTIVE, INACTIVE
    }

    public enum VehicleType {
        BUS, MICROBUS
    }
}

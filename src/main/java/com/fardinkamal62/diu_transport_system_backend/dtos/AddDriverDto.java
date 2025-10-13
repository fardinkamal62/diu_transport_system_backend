package com.fardinkamal62.diu_transport_system_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class AddDriverDto {
    @Setter
    @Getter
    @NotBlank(message = "full_name is required")
    @Size(min = 2, max = 100, message = "full_name must be between 2 and 100 characters")
    private String full_name;

    @Getter
    @Setter
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    public enum Status {
        active, inactive
    }

    public enum VehicleType {
        bus, microbus
    }

    @Getter
    @Setter
    private String status = String.valueOf(Status.inactive);

    @NotNull(message = "preferredVehicle is required")
    @Size(min = 1, message = "At least one preferred vehicle must be specified")
    @Getter
    @Setter
    private List<VehicleType> preferredVehicle = new java.util.ArrayList<>();

    @Getter
    @Setter
    private String phoneNumber;
    @Setter
    @Getter
    private String email;

    public List<VehicleType> getPreferredVehicles() {
        return preferredVehicle;
    }
}

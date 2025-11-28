package com.fardinkamal62.diu_transport_system_backend.dtos;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

public class AdminLoginRequestDto {
    @Setter
    @Getter
    @Email(message = "Email should be valid")
    @Size(min = 10, max = 100, message = "Email must be between 10 and 100 characters")
    private String email;

    @Setter
    @Getter
    @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters")
    private String username;

    @Getter
    @Setter
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;

    public boolean isValid() {
        return (email != null && !email.trim().isEmpty()) ||
                (username != null && !username.trim().isEmpty());
    }
}
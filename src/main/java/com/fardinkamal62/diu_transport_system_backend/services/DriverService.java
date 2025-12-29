package com.fardinkamal62.diu_transport_system_backend.services;

import com.fardinkamal62.diu_transport_system_backend.dtos.AddDriverDtoRequest;
import com.fardinkamal62.diu_transport_system_backend.dtos.AddDriverResponseDto;
import com.fardinkamal62.diu_transport_system_backend.entities.User;
import com.fardinkamal62.diu_transport_system_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DriverService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DriverService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AddDriverResponseDto addDriver(AddDriverDtoRequest addDriver) {
        // Validate user doesn't already exist
        if (userRepository.existsByEmail(addDriver.getEmail()) ||
                userRepository.existsByPhoneNumber(addDriver.getPhoneNumber())) {
            throw new IllegalArgumentException("User already exists with this email or phone number");
        }

        User user = new User();
        user.setName(addDriver.getFullName());
        user.setPassword(passwordEncoder.encode(addDriver.getPassword()));
        user.setStatus(User.Status.valueOf(addDriver.getStatus().name()));
        user.setGroups(java.util.List.of("driver"));
        user.setPreferredVehicles(
                addDriver.getPreferredVehicles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
        user.setPhoneNumber(addDriver.getPhoneNumber());
        user.setEmail(addDriver.getEmail());

        User savedUser = userRepository.save(user);

        return AddDriverResponseDto.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .status(AddDriverResponseDto.DriverStatus.valueOf(savedUser.getStatus().name()))
                .preferredVehicles(
                        addDriver.getPreferredVehicles().stream()
                                .map(vt -> AddDriverResponseDto.VehicleType.valueOf(vt.name()))
                                .collect(Collectors.toList())
                )
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
}
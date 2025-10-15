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
        User user = new User();
        user.setName(addDriver.getFull_name());
        user.setPassword(passwordEncoder.encode(addDriver.getPassword()));
        user.setStatus(User.Status.valueOf(addDriver.getStatus()));
        user.setGroups(java.util.List.of("driver"));
        user.setPreferredVehicles(
                addDriver.getPreferredVehicles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
        user.setPhoneNumber(addDriver.getPhoneNumber());
        user.setEmail(addDriver.getEmail());
        if (userRepository.existsByEmail(addDriver.getEmail()) ||
                userRepository.existsByPhoneNumber(addDriver.getPhoneNumber())) {
            throw new IllegalArgumentException("User already exists");
        }

        User savedUser = userRepository.save(user);

        AddDriverResponseDto response = new AddDriverResponseDto();
        response.setId(savedUser.getId());
        response.setFull_name(savedUser.getName());
        response.setStatus(
                AddDriverResponseDto.DriverStatus.valueOf(savedUser.getStatus().name())
        );
        response.setPhoneNumber(savedUser.getPhoneNumber());
        return response;
    }
}
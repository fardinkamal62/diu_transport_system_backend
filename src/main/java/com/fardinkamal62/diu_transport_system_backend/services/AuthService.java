package com.fardinkamal62.diu_transport_system_backend.services;

import com.fardinkamal62.diu_transport_system_backend.dtos.AdminLoginRequestDto;
import com.fardinkamal62.diu_transport_system_backend.dtos.AuthResponseDto;
import com.fardinkamal62.diu_transport_system_backend.entities.User;
import com.fardinkamal62.diu_transport_system_backend.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponseDto adminLogin(AdminLoginRequestDto loginRequest) {
        String identifier = loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty() ?
                            loginRequest.getEmail() : loginRequest.getUsername();

        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Email or username is required");
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(identifier, loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        User user = (User) authentication.getPrincipal();

        // Check if user is admin
        if (Arrays.stream(User.Role.values()).noneMatch(role -> role == User.Role.admin)) {
            throw new IllegalArgumentException("User is not an admin");
        }

        String jwt = jwtUtils.generateToken(user);

        return new AuthResponseDto(jwt);
    }
}


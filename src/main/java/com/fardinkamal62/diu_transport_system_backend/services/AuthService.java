package com.fardinkamal62.diu_transport_system_backend.services;

import com.fardinkamal62.diu_transport_system_backend.dtos.AdminLoginRequestDto;
import com.fardinkamal62.diu_transport_system_backend.dtos.AuthResponseDto;
import com.fardinkamal62.diu_transport_system_backend.entities.User;
import com.fardinkamal62.diu_transport_system_backend.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponseDto adminLogin(AdminLoginRequestDto loginRequest) {
        if (!loginRequest.hasValidIdentifier()) {
            throw new IllegalArgumentException("Email or username is required");
        }

        String identifier = loginRequest.getEmail() != null && !loginRequest.getEmail().isEmpty() ?
                            loginRequest.getEmail() : loginRequest.getUsername();

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

        // Assuming JWT token expiry is 24 hours (86400 seconds) - adjust based on your config
        long expiresIn = 86400L;

        // Get role from groups
        String role = user.getGroups() != null && !user.getGroups().isEmpty()
                ? user.getGroups().get(0).toUpperCase()
                : "USER";

        return AuthResponseDto.builder()
                .token(jwt)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .username(user.getUsername())
                .role(role)
                .issuedAt(java.time.LocalDateTime.now())
                .build();
    }
}


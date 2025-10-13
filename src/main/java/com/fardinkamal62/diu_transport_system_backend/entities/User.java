package com.fardinkamal62.diu_transport_system_backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone_number")
        })
@NamedQuery(name = "User.findActiveUsers",
        query = "SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.status = 'ACTIVE'")
public class User implements UserDetails {

    // Getters and Setters
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "full_name is required")
    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Setter
    @Getter
    @Size(max = 100, message = "Username must not exceed 100 characters")
    @Column(name = "username", unique = true, length = 100)
    private String username;

    @Setter
    @Getter
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(name = "email", unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email format is invalid")
    private String email;

    @Setter
    @Getter
    @URL(message = "Picture should be a valid URL")
    @Column(name = "picture")
    private String picture = "";

    @Setter
    @Getter
    @Column(name = "phone_number", unique = true, length = 15)
    @Pattern(regexp = "^(\\+8801|01)[3-9][0-9]{8}$",
            message = "Phone number must be a valid Bangladeshi number")
    private String phoneNumber;

    @Setter
    @Getter
    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "groups", columnDefinition = "text[]")
    private List<String> groups = new ArrayList<>();

    @Getter
    @Setter
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status = Status.active;

    @Setter
    @Column(name = "preferred_vehicles", columnDefinition = "text[]")
    private List<String> preferredVehicles = new ArrayList<>();

    public enum Status {
        active, inactive
    }

    public enum PreferredVehicle {
        bus, microbus
    }

    public enum Role {
        admin, driver, lineman
    }

    public User() {
    }

    public User(String full_name, String password, List<PreferredVehicle> preferredVehicles) {
        this.full_name = full_name;
        this.password = password;
        this.preferredVehicles = Collections.singletonList(preferredVehicles.stream()
                .map(Enum::name)
                .collect(Collectors.joining(",")));
    }

    @AssertTrue(message = "Either email or phone number is required")
    private boolean isEmailOrPhoneProvided() {
        return (email != null && !email.trim().isEmpty()) ||
                (phoneNumber != null && !phoneNumber.trim().isEmpty());
    }

    public boolean isActive() {
        return Status.active.equals(this.status) && this.deletedAt == null;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.status = Status.inactive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGroups().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public String getName() {
        return full_name;
    }

    public void setName(String name) {
        this.full_name = name;
    }

    public List<String> getGroups() {
        return groups == null ? new ArrayList<>() : groups;
    }

    public List<String> getPreferredVehicles() {
        if (preferredVehicles.isEmpty()) return new ArrayList<>();
        return preferredVehicles;
    }

}
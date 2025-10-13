package com.fardinkamal62.diu_transport_system_backend.repositories;

import com.fardinkamal62.diu_transport_system_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND (u.email = :identifier OR u.phoneNumber = :identifier OR u.username = :identifier)")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    // Soft delete queries
    @Query("SELECT u FROM User u WHERE u.deletedAt IS NOT NULL")
    List<User> findDeletedUsers();
}
package com.backend.authservice.core.data.repository;

import com.backend.authservice.core.data.entity.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity, UUID> {

    Optional<PasswordEntity> findByUserId(UUID userId);
}

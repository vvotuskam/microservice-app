package com.backend.authservice.core.data.repository;

import com.backend.authservice.core.data.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {

    Optional<TokenEntity> findByJwt(String jwt);
}

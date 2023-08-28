package com.backend.authservice.core.data.entity;

import com.backend.authservice.core.data.enums.TokenTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String jwt;

    @Enumerated(EnumType.STRING)
    private TokenTypeEnum type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID userId;
}

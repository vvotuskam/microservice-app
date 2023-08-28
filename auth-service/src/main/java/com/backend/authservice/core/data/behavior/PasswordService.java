package com.backend.authservice.core.data.behavior;

import com.backend.authservice.core.data.entity.PasswordEntity;
import com.backend.authservice.core.domain.wrapper.Data;

import java.util.UUID;

public interface PasswordService {

    Data<PasswordEntity> findByUserId(UUID userId);
}

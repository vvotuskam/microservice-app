package com.backend.authservice.core.data.behavior;

import com.backend.authservice.core.data.entity.TokenEntity;
import com.backend.authservice.core.data.enums.TokenTypeEnum;
import com.backend.authservice.core.domain.wrapper.Data;

import java.util.UUID;

public interface TokenService {

    Data<TokenEntity> findByToken(String jwt);

    Data<TokenEntity> save(String jwt, TokenTypeEnum tokenType, UUID userId);
}

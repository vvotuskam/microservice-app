package com.backend.authservice.core.data.service;

import com.backend.authservice.core.data.behavior.TokenService;
import com.backend.authservice.core.data.entity.TokenEntity;
import com.backend.authservice.core.data.enums.TokenTypeEnum;
import com.backend.authservice.core.data.repository.TokenRepository;
import com.backend.authservice.core.domain.failure.ServiceFailure;
import com.backend.authservice.core.domain.failure.TokenNotFoundFailure;
import com.backend.authservice.core.domain.wrapper.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Data<TokenEntity> save(String jwt, TokenTypeEnum tokenType, UUID userId) {
        try {
            TokenEntity tokenEntity = TokenEntity.builder()
                    .jwt(jwt)
                    .type(tokenType)
                    .userId(userId)
                    .build();
            return new Data.Success<>(tokenRepository.save(tokenEntity));
        } catch (Exception e) {
            log.error("Unexpected exception", e);
            return new Data.Error<>(new ServiceFailure());
        }
    }

    @Override
    public Data<TokenEntity> findByToken(String jwt) {
        try {
            var tokenOptional = tokenRepository.findByJwt(jwt);

            if (tokenOptional.isEmpty()) return new Data.Error<>(new TokenNotFoundFailure());

            return new Data.Success<>(tokenOptional.get());
        } catch (Exception e) {
            log.error("Unexpected exception", e);
            return new Data.Error<>(new ServiceFailure());
        }
    }
}

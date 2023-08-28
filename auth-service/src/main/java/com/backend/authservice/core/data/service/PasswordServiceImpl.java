package com.backend.authservice.core.data.service;

import com.backend.authservice.core.data.behavior.PasswordService;
import com.backend.authservice.core.data.entity.PasswordEntity;
import com.backend.authservice.core.data.repository.PasswordRepository;
import com.backend.authservice.core.domain.failure.PasswordNotFoundFailure;
import com.backend.authservice.core.domain.failure.ServiceFailure;
import com.backend.authservice.core.domain.wrapper.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;

    @Override
    public Data<PasswordEntity> findByUserId(UUID userId) {
        try {
            var result = passwordRepository.findByUserId(userId);
            if (result.isEmpty()) return new Data.Error<>(new PasswordNotFoundFailure());
            return new Data.Success<>(result.get());
        } catch (Exception e) {
            return new Data.Error<>(new ServiceFailure());
        }
    }
}

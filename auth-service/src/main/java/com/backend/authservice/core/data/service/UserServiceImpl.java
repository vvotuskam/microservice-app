package com.backend.authservice.core.data.service;

import com.backend.authservice.core.data.behavior.UserService;
import com.backend.authservice.core.data.entity.UserEntity;
import com.backend.authservice.core.data.repository.UserRepository;
import com.backend.authservice.core.domain.failure.ServiceFailure;
import com.backend.authservice.core.domain.failure.UserNotFoundFailure;
import com.backend.authservice.core.domain.wrapper.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Data<UserEntity> findByEmail(String email) {
        try {
            var result = userRepository.findByEmail(email);
            if (result.isEmpty()) return new Data.Error<>(new UserNotFoundFailure());
            return new Data.Success<>(result.get());
        } catch (Exception e) {
            return new Data.Error<>(new ServiceFailure());
        }
    }
}

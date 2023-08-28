package com.backend.authservice.core.data.behavior;

import com.backend.authservice.core.data.entity.UserEntity;
import com.backend.authservice.core.domain.wrapper.Data;

public interface UserService {

    Data<UserEntity> findByEmail(String email);
}

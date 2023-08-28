package com.backend.authservice.feature.authentication.domain.failure;

import com.backend.authservice.core.domain.failure.base.Failure;

public class AuthorizationFailure implements Failure {

    @Override
    public Integer getCode() {
        return 401;
    }

    @Override
    public String getMessage() {
        return "incorrect email or password";
    }
}

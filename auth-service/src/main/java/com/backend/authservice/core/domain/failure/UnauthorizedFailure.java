package com.backend.authservice.core.domain.failure;

import com.backend.authservice.core.domain.failure.base.Failure;

public class UnauthorizedFailure implements Failure {
    @Override
    public Integer getCode() {
        return 403;
    }

    @Override
    public String getMessage() {
        return "unauthorized";
    }
}

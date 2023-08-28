package com.backend.authservice.core.domain.failure;

import com.backend.authservice.core.domain.failure.base.Failure;

public class ServiceFailure implements Failure {

    @Override
    public Integer getCode() {
        return 500;
    }

    @Override
    public String getMessage() {
        return "service unavailable";
    }
}

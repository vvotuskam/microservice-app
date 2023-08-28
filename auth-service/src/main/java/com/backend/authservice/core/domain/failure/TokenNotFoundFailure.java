package com.backend.authservice.core.domain.failure;

import com.backend.authservice.core.domain.failure.base.Failure;

public class TokenNotFoundFailure implements Failure {
    @Override
    public Integer getCode() {
        return 400;
    }

    @Override
    public String getMessage() {
        return "token not found";
    }
}

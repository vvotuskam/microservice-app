package com.backend.authservice.core.domain.usecase;

import com.backend.authservice.core.domain.wrapper.Data;

public interface UseCase<Request, Response> {

    Data<Response> invoke(Request request);
}

package com.backend.authservice.feature.authentication.rest.controller;

import com.backend.authservice.core.domain.failure.base.Failure;
import com.backend.authservice.core.domain.wrapper.Data;
import com.backend.authservice.feature.authentication.domain.usecase.AuthUseCase;
import com.backend.authservice.feature.authentication.rest.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Validated AuthRequest request) {
        var data = authUseCase.invoke(request);

        if (data instanceof Data.Error) {
            Failure failure = data.getFailure();
            return ResponseEntity.status(failure.getCode()).body(failure);
        }

        return ResponseEntity.ok(data.getValue());
    }
}

package com.backend.authservice.core.domain.wrapper;

import com.backend.authservice.core.domain.failure.base.Failure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Data<T> {

    public T getValue() {
        if (this instanceof Success) return this.getValue();
        return null;
    }

    public Failure getFailure() {
        if (this instanceof Error) return this.getFailure();
        return null;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Success<T> extends Data<T> {
        private T value;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Error<T> extends Data<T> {
        private Failure failure;
    }
}


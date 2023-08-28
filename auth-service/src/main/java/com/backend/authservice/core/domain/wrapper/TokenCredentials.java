package com.backend.authservice.core.domain.wrapper;

import com.backend.authservice.core.data.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenCredentials {

    private String email;
    private UserRoleEnum role;
}

package com.backend.authservice.core.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("token.jwt")
@Getter
@Setter
public class JwtProperties {

    private Long access;
    private Long refresh;
    private String secret;
}

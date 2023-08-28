package com.backend.authservice.core.domain.utils;

import com.backend.authservice.core.config.property.JwtProperties;
import com.backend.authservice.core.data.enums.TokenTypeEnum;
import com.backend.authservice.core.data.enums.UserRoleEnum;
import com.backend.authservice.core.domain.failure.TokenExpiredFailure;
import com.backend.authservice.core.domain.failure.TokenInvalidFailure;
import com.backend.authservice.core.domain.wrapper.Data;
import com.backend.authservice.core.domain.wrapper.Data.Error;
import com.backend.authservice.core.domain.wrapper.Data.Success;
import com.backend.authservice.core.domain.wrapper.TokenCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String generateJwt(String email, UserRoleEnum role, TokenTypeEnum tokenType) {

        LocalDateTime expiration = switch (tokenType) {
            case ACCESS -> LocalDateTime.now().plusHours(jwtProperties.getAccess());
            default -> LocalDateTime.now().plusHours(jwtProperties.getRefresh());
        };

        JwtBuilder jwtBuilder = Jwts
                .builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()));

        return jwtBuilder.compact();
    }

    public Data<TokenCredentials> getCredentials(String jwt) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            UserRoleEnum role = UserRoleEnum.valueOf(claims.get("role", String.class));
            String email = claims.getSubject();

            return new Success<>(new TokenCredentials(email, role));
        } catch (ExpiredJwtException e) {
            log.error("Token is expired", e);
            return new Error<>(new TokenExpiredFailure());
        } catch (JwtException e) {
            log.error("Token is invalid", e);
            return new Error<>(new TokenInvalidFailure());
        }
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

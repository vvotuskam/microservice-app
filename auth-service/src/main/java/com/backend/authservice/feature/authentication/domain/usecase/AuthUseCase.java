package com.backend.authservice.feature.authentication.domain.usecase;

import com.backend.authservice.core.data.behavior.PasswordService;
import com.backend.authservice.core.data.behavior.TokenService;
import com.backend.authservice.core.data.behavior.UserService;
import com.backend.authservice.core.domain.failure.UserNotFoundFailure;
import com.backend.authservice.core.domain.usecase.UseCase;
import com.backend.authservice.core.domain.utils.JwtUtils;
import com.backend.authservice.core.domain.wrapper.Data;
import com.backend.authservice.feature.authentication.domain.failure.AuthorizationFailure;
import com.backend.authservice.feature.authentication.rest.dto.AuthRequest;
import com.backend.authservice.feature.authentication.rest.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.backend.authservice.core.data.enums.TokenTypeEnum.ACCESS;
import static com.backend.authservice.core.data.enums.TokenTypeEnum.REFRESH;

@Component
@RequiredArgsConstructor
public class AuthUseCase implements UseCase<AuthRequest, AuthResponse> {

    private final JwtUtils jwtUtils;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordService passwordService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Data<AuthResponse> invoke(AuthRequest request) {
        var userData = userService.findByEmail(request.getEmail());
        if (userData instanceof Data.Error) {
            if (userData.getFailure() instanceof UserNotFoundFailure)
                return new Data.Error<>(new AuthorizationFailure());
            return new Data.Error<>(userData.getFailure());
        }

        var user = userData.getValue();

        var passwordData = passwordService.findByUserId(user.getId());
        if (passwordData instanceof Data.Error) {
            return new Data.Error<>(passwordData.getFailure());
        }

        var password = passwordData.getValue();

        if (!passwordEncoder.matches(request.getPassword(), password.getPassword())) {
            return new Data.Error<>(new AuthorizationFailure());
        }

        String access = jwtUtils.generateJwt(user.getEmail(), user.getRole(), ACCESS);
        String refresh = jwtUtils.generateJwt(user.getEmail(), user.getRole(), REFRESH);

        var accessSaved = tokenService.save(access, ACCESS, user.getId());
        if (accessSaved instanceof Data.Error) return new Data.Error<>(accessSaved.getFailure());

        var refreshSaved = tokenService.save(refresh, REFRESH, user.getId());
        if (refreshSaved instanceof Data.Error) return new Data.Error<>(accessSaved.getFailure());

        return new Data.Success<>(new AuthResponse(access, refresh));
    }
}

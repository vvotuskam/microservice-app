package com.backend.authservice.core.config.security;

import com.backend.authservice.core.data.behavior.TokenService;
import com.backend.authservice.core.data.entity.TokenEntity;
import com.backend.authservice.core.data.enums.TokenTypeEnum;
import com.backend.authservice.core.domain.utils.JwtUtils;
import com.backend.authservice.core.domain.wrapper.Data;
import com.backend.authservice.core.domain.wrapper.TokenCredentials;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String bearerHeader = request.getHeader("Authorization");
        if (bearerHeader == null || !bearerHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = bearerHeader.substring(7);

        var jwtData = jwtUtils.getCredentials(jwt);
        if (jwtData instanceof Data.Error) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenCredentials credentials = jwtData.getValue();

        var tokenData = tokenService.findByToken(jwt);
        if (tokenData instanceof Data.Error) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenEntity token = tokenData.getValue();

        if (token.getType() != TokenTypeEnum.ACCESS) {
            filterChain.doFilter(request, response);
            return;
        }

        var userDetails = userDetailsService.loadUserByUsername(credentials.getEmail());

        var authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

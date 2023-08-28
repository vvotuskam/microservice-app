package com.backend.authservice.core.config.security;

import com.backend.authservice.core.data.behavior.PasswordService;
import com.backend.authservice.core.data.behavior.UserService;
import com.backend.authservice.core.domain.wrapper.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userData = userService.findByEmail(username);
        if (userData instanceof Data.Error) {
            throw new UsernameNotFoundException("not found");
        }

        var user = userData.getValue();

        var passwordData = passwordService.findByUserId(user.getId());
        if (passwordData instanceof Data.Error) {
            throw new UsernameNotFoundException("not found");
        }

        var password = passwordData.getValue();

        return new SecurityUserDetails(user, password);
    }
}

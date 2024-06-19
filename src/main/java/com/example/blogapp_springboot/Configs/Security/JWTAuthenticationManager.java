package com.example.blogapp_springboot.Configs.Security;

import com.example.blogapp_springboot.users.UsersService;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {
    private UsersService usersService;
    private JWTService jwtService;

    public JWTAuthenticationManager(UsersService usersService, JWTService jwtService) {
        this.usersService = usersService;
        this.jwtService = jwtService;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JWTAuthentication) {

            var jwtAuthentication = (JWTAuthentication) authentication;
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);
            var userEntity = usersService.getUser(userId);
            jwtAuthentication.userEntity = userEntity;
            jwtAuthentication.setAuthenticated(true);

            return jwtAuthentication;
        }
        throw new IllegalAccessError(("Cannot authenticate with non-JWT authentication"));
    }
}
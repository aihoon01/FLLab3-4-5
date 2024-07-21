package com.example.blogapp_springboot.Security;


import com.example.blogapp_springboot.Configs.Security.JWTService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTServiceTests {
    JWTService jwtService = new JWTService();

    @Test
    @Order(1)
    void canCreateJwtFromUserId() {
        var jwt = jwtService.createJwt(219L);
        assertNotNull(jwt);
    }
}

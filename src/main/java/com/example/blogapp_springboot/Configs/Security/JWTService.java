package com.example.blogapp_springboot.Configs.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    private static final String JWT_KEY = "jshslskiowijalksjoi320932kns1lkjsl1.123";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJwt(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString) {
        var decodedJwt = JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJwt.getSubject());
        return userId;
    }
}

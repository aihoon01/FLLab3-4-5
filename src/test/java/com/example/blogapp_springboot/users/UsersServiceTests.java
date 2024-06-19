package com.example.blogapp_springboot.users;


import com.example.blogapp_springboot.users.dtos.CreateUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTests {
    @Autowired
    UsersService usersService;

    @Test
    void can_create_users() {
        var userServiceRequest = new CreateUserDto("Steve01", "Password", "steve01@gmail.com");
        var user = usersService.createUser(userServiceRequest);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("Steve01", user.getUsername());
    }
}

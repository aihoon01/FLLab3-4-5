package com.example.blogapp_springboot.users;


import com.example.blogapp_springboot.users.dtos.CreateUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {
    @Autowired
    UsersService usersService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    private UsersRepository usersRepository;

    @Test
    void can_create_users() {
        //Assert
        var userServiceRequest = new CreateUserDto("Steve01", "Password", "steve01@gmail.com");
        var dto = createUserEntity(userServiceRequest);
        Mockito.when(usersRepository.save(Mockito.any(UserEntity.class))).thenReturn(dto);

        //Act
        var user = usersService.createUser(userServiceRequest);

        //Assert

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Steve01", user.getUsername());
        Assertions.assertEquals(dto, user);
    }

    @Test
    void shouldReturnAUserByUsername() throws UsersService.UserNotFoundException {
        var userServiceRequest = new CreateUserDto("Steve01", "Password", "steve01@gmail.com");
        var dto = createUserEntity(userServiceRequest);

        Mockito.when(usersRepository.findByUsername("Steve01")).thenReturn(Optional.of(dto));
        var user = usersService.getUser("Steve01");

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user, dto);
    }

    @Test
    void shouldReturnUserById() throws UsersService.UserNotFoundException {
        var userServiceRequest = new CreateUserDto("Steve01", "Password", "steve01@gmail.com");
        var dto = createUserEntity(userServiceRequest);

        dto.setId(0021201L);
        Mockito.when(usersRepository.findById(0021201L)).thenReturn(Optional.of(dto));

        var user = usersService.getUser(0021201L);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user, dto);
    }

    private UserEntity createUserEntity(CreateUserDto userServiceRequest) {
        var dto = modelMapper.map(userServiceRequest, UserEntity.class);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return dto;
    }

}

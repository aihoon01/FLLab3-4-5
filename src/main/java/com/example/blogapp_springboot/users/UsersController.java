package com.example.blogapp_springboot.users;

import com.example.blogapp_springboot.Configs.ErrorResponse;
import com.example.blogapp_springboot.Configs.Security.JWTService;
import com.example.blogapp_springboot.users.dtos.CreateUserDto;
import com.example.blogapp_springboot.users.dtos.UserResponse;
import com.example.blogapp_springboot.users.dtos.LoginUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private UsersService usersService;

    public UsersController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserDto dto) {
        UserEntity savedUser = usersService.createUser(dto);
        URI savedUserUrl = URI.create("/users/" + savedUser.getId());
        return ResponseEntity.created(savedUserUrl).body(modelMapper.map(savedUser, UserResponse.class));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserDto dto) throws
        UsersService.UserNotFoundException,
        UsersService.InvalidCredentialsException
    {
        UserEntity loggedInUser = usersService.loginUser(dto.getUsername(), dto.getPassword());
        var userResponse = modelMapper.map(loggedInUser, UserResponse.class);
        userResponse.setToken(jwtService.createJwt(userResponse.getId()));
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("")
    String getUsers() {
        return "ALL USERS";
    }

    @ExceptionHandler({
            UsersService.UserNotFoundException.class,
            UsersService.InvalidCredentialsException.class
    })
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if (ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UsersService.InvalidCredentialsException) {
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } else {
            message = "Something went wrong!";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}

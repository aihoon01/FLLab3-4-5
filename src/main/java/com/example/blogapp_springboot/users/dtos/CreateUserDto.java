package com.example.blogapp_springboot.users.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class CreateUserDto {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
}

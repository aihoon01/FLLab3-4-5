package com.example.blogapp_springboot.users;


import com.example.blogapp_springboot.users.dtos.CreateUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserDto u) {
        var newUser = modelMapper.map(u,UserEntity.class);
        newUser.setPassword(passwordEncoder.encode((u.getPassword())));
        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String username) throws UserNotFoundException {
            return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long id) throws UserNotFoundException {
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserEntity loginUser(String username, String password) throws UserNotFoundException, InvalidCredentialsException {
            var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
            var passMatch = passwordEncoder.matches(password, user.getPassword());
            if(!passMatch) throw new InvalidCredentialsException();
            return user;
    }

    public static class UserNotFoundException extends IllegalAccessException {
        public UserNotFoundException(String username) {
            super("User " + username + " not found");
        }
        public UserNotFoundException(Long id) {
            super("User " + id + " not found");
        }
    }

    public static class InvalidCredentialsException extends IllegalAccessException {
        public InvalidCredentialsException() {
            super("Invalid username or password combination");
        }
    }
}

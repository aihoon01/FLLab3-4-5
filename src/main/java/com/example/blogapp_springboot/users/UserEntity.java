package com.example.blogapp_springboot.users;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity(name = "Users")
@Data
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access =AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String username;

    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    @Column(name = "password", nullable = false)
    @NonNull
    private String password;

    @Column(name = "bio")
    @Nullable
    private String bio;

    @Column(name = "image")
    @Nullable
    private String image;

}


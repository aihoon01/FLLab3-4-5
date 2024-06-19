package com.example.blogapp_springboot.articles;

import com.example.blogapp_springboot.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity(name = "ARTICLES")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    @NonNull
    private String title;

    @Column(name = "slug", nullable = false)
    @NonNull
    private String slug;

    @Column(name = "subtitle")
    @Nullable
    private String subtitle;

    @Column(name = "body", nullable = false)
    @NonNull
    private String body;

    @Column(name = "createdAt")
    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity author;
}

package com.example.blogapp_springboot.articles.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Data
@Setter(AccessLevel.NONE)
public class CreateArticleDto {
    @NonNull
    private String title;
    @NonNull
    private String body;
    @Nullable
    private String subtitle;
}

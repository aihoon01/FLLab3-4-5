package com.example.blogapp_springboot.articles;

import com.example.blogapp_springboot.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    @GetMapping("")
    public String getAllArticles() {
        return "Articles";
    }

    @GetMapping("/{id}")
    String getArticleById(@PathVariable String id, @AuthenticationPrincipal UserEntity user) {
        return "Article " + id + " by " + user.getUsername();
    }

    @PostMapping("")
    String createArticle(@AuthenticationPrincipal UserEntity user) {
        System.out.println("JERJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        return "Article Created by " + user.getUsername();
    }
}

package com.example.blogapp_springboot.articles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<ArticleEntity, Long> {
   Optional<ArticleEntity> findBySlug(String slug);
}

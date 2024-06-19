package com.example.blogapp_springboot.articles;

import com.example.blogapp_springboot.articles.dtos.CreateArticleDto;
import com.example.blogapp_springboot.articles.dtos.UpdateArticleDto;
import com.example.blogapp_springboot.users.UsersRepository;
import com.example.blogapp_springboot.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticlesService {
    private final ModelMapper modelMapper;
    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;

    public ArticlesService(ArticlesRepository articlesRepository, ModelMapper modelMapper) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article =  articlesRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
         return article;
    }

    public ArticleEntity createArticle(CreateArticleDto a, Long authorId) throws UsersService.UserNotFoundException {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));
        return articlesRepository.save(modelMapper.map(a,ArticleEntity.class));
    }

    public ArticleEntity updateArticlce(UpdateArticleDto a, Long articleId) throws ArticleNotFoundException {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(a.getTitle() != null) article.setTitle(a.getTitle());
        if(a.getSubtitle() != null) article.setSubtitle(a.getSubtitle());
        if(a.getBody() != null) article.setBody(a.getBody());
        return articlesRepository.save(article);
    }

    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + " not found");
        }

        public ArticleNotFoundException(Long id) {
            super("Article not found");
        }
    }
}

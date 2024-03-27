package app.controllers;

import app.dtos.article.ArticleHeadline;
import app.dtos.article.MapperArticle;
import app.models.Article;
import app.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    ArticleRepository articleRepository;
    MapperArticle mapper;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, MapperArticle mapper) {
        this.articleRepository = articleRepository;
        this.mapper = mapper;
    }
    @Transactional
    // Get single article
    @GetMapping("/public/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id){
        Optional<Article> article = articleRepository.findById(id);
        // Article not found
        return article.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //add new article to database
    @Transactional
    @PostMapping("/articles")
    @PreAuthorize("hasAuthority('AUTHOR')")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article createdArticle = articleRepository.save(article);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdArticle.getId()).toUri();
        return ResponseEntity.created(location).body(createdArticle);
    }

    @Transactional
    @GetMapping("/public/articlesHeadlines")
    public ResponseEntity<List<ArticleHeadline>> getArticlesHeadlines() {
        List<ArticleHeadline> headlines = articleRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(headlines);
    }
}

package app.controllers;

import app.models.Article;
import app.repository.ArticleRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepositoryJpa articleRepository;

    @Transactional
    // Get single article
    @GetMapping("/public/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id){
        Article article = articleRepository.findById(id);
        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            // Article not found
            return ResponseEntity.notFound().build();
        }
    }

    //add new article to database
    @Transactional
    @PostMapping("/public/articles")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article createdArticle = articleRepository.save(article);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdArticle.getId()).toUri();
        return ResponseEntity.created(location).body(createdArticle);
    }

    @Transactional
    @GetMapping("/public/articlesHeadlines")
    public ResponseEntity<List<Map<String, Object>>> getArticlesHeadlines() {
        List<Object[]> headlines = articleRepository.findArticleHeadlines();
        List<Map<String, Object>> response = headlines.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", result[0]);
            map.put("title", result[1]);
            map.put("author", result[2]);
            map.put("date", result[3]);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}

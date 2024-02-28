package app.Controllers;

import app.Models.Article;
import app.Repository.ArticleRepositoryJpa;
import app.Repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepositoryJpa articleRepository;

    // Get single article
    @Transactional
    @PostMapping("/article")
    public Article getArticle(@RequestBody Long id){
        // implement return single article
        return null;
    }

    // get list of articles without full text
    @Transactional
    @GetMapping("/articlesHeadlines")
    public List<Article> GetArticles(){
        // implement return list of articles
        return null;
    }



}

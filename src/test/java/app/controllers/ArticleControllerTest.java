package app.controllers;

import app.models.Article;
import app.repository.ArticleRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    void getSingleArticle() throws Exception {
        // Create a new article
        Article article = new Article(null, "Sample Article", "This is a sample article.", "Author", new Date());
        article = articleRepository.save(article);

        // Test fetching the article
        mockMvc.perform(get("/public/article/" + article.getId()).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Article")))
                .andExpect(jsonPath("$.body", is("This is a sample article.")))
                .andExpect(jsonPath("$.author", is("Author")));
    }

    @Test
    @WithUserDetails("author@author.nl")
    void addArticle() throws Exception {
        JSONObject json = new JSONObject();
        json.put("title", "New Article");
        json.put("body", "This is a new article.");
        json.put("author", "Author");
        json.put("date", "2024-06-02T12:14:06.000Z");

        mockMvc.perform(post("/articles").contentType("application/json").content(json.toJSONString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Article")))
                .andExpect(jsonPath("$.body", is("This is a new article.")))
                .andExpect(jsonPath("$.author", is("Author")));
    }

    @Test
    @WithUserDetails("admin@admin.nl")
    void unauthorizedAddArticle() throws Exception {
        JSONObject json = new JSONObject();
        json.put("title", "Unauthorized Article");
        json.put("body", "This article should not be added.");
        json.put("author", "Author");
        json.put("date", "2024-06-02T12:14:06.000Z");

        mockMvc.perform(post("/articles").contentType("application/json").content(json.toJSONString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getArticlesHeadlines() throws Exception {
        // Create a few articles
        Article article1 = new Article(null, "First Article", "Content of the first article.", "Author1", new Date());
        articleRepository.save(article1);

        Article article2 = new Article(null, "Second Article", "Content of the second article.", "Author2", new Date());
        articleRepository.save(article2);

        // Test fetching the headlines
        mockMvc.perform(get("/public/articlesHeadlines").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("First Article")))
                .andExpect(jsonPath("$[1].title", is("Second Article")));
    }
}

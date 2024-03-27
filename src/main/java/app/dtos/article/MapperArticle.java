package app.dtos.article;

import app.models.Article;
import org.springframework.stereotype.Component;

@Component
public class MapperArticle {

    // Converts an Article entity into an ArticleHeadlines DTO
    public ArticleHeadline toDto(Article article) {
        ArticleHeadline dto = new ArticleHeadline();
        dto.setTitle(article.getTitle());
        dto.setAuthor(article.getAuthor());
        dto.setDate(article.getDate());
        return dto;
    }
}
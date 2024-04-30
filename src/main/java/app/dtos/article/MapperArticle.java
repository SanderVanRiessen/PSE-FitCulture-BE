package app.dtos.article;

import app.models.Article;
import org.springframework.stereotype.Component;

@Component
public class MapperArticle {

    public ArticleHeadline toDto(Article article) {
        ArticleHeadline dto = new ArticleHeadline();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setAuthor(article.getAuthor());
        dto.setDate(article.getDate());
        return dto;
    }
}
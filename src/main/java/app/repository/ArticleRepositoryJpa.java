package app.repository;

import app.models.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ArticleRepositoryJpa extends AbstractEntityRepositoryJpa<Article> {

    @PersistenceContext
    private EntityManager entityManager;

    // Method to fetch headlines (title, author, date)
    public List<Object[]> findArticleHeadlines() {
        return entityManager.createQuery(
                        "SELECT a.id, a.title, a.author, a.date FROM Article a",
                        Object[].class)
                .getResultList();
    }

}


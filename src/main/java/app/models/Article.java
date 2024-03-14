package app.models;

import app.repository.Identifiable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    private String author;
    private Date date;

    public Article() {
    }

    public Article(Long id, String title, String body, String author, Date date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
        this.date = date;
    }
}

package app.Models;

import app.Repository.Identifiable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles")
public class Article implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Title;
    private String body;

    private String author;
    private Date date;

    public Article() {
    }

    public Article(Long id, String title, String body, String author, Date date) {
        this.id = id;
        Title = title;
        this.body = body;
        this.author = author;
        this.date = date;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}

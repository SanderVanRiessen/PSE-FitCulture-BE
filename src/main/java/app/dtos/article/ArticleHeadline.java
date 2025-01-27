package app.dtos.article;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ArticleHeadline {

    private Long id;
    private String title;
    private String author;
    private Date date;
}
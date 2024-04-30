package app.dtos.forum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithTopicsDTO {
    private Long id;
    private String name;
    private String description;
    private List<TopicDTO> recentTopics;
}

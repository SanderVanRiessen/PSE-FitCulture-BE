package app.dtos.forum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDetailDTO {
    private Long id;
    private String name;
    private String description;
    private String username;
    private LocalDateTime createdAt;
    private long postCount;
}

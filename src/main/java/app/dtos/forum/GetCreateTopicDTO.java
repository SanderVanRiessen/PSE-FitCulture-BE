package app.dtos.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCreateTopicDTO {
    private Long id;
    private String title;
    private String text;
    private String userName;
}


package app.dtos.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUser {
    private Long id;
    private String name;
    private String email;
}

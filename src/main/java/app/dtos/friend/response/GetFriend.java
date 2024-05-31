package app.dtos.friend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFriend {
    private String name;
    private String email;
    private String status;
}

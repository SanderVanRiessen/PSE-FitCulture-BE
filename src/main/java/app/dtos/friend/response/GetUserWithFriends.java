package app.dtos.friend.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUserWithFriends {
    private Long id;
    private String name;
    private String email;
    private List<GetFriend> friends;
}

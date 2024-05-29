package app.dtos.friend.response;

import app.dtos.user.response.GetUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUserWithFriends  extends GetUser {
    private List<GetFriend> friends;
}

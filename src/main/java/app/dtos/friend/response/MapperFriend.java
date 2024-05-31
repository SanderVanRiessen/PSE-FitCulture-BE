package app.dtos.friend.response;

import app.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperFriend {
    public GetUserWithFriends toPlainUserWithFriends(User user){
        GetUserWithFriends dto = new GetUserWithFriends();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        List<GetFriend> friends = new ArrayList<>();
        user.getFriends().forEach((e) -> {
            GetFriend friend = new GetFriend();
            friend.setEmail(e.getOtherUserId().getEmail());
            friend.setName(e.getOtherUserId().getName());
            friend.setStatus(e.getStatus().toString());
            friends.add(friend);
        });
        dto.setFriends(friends);
        return dto;
    }
}

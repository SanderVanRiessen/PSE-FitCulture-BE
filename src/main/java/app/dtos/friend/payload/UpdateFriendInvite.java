package app.dtos.friend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFriendInvite {
    private Long friendId;
    private String status;
}

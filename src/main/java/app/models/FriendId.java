package app.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FriendId implements Serializable {

    private Long myUserId;
    private Long otherUserId;

    public FriendId() {

    }

    public FriendId(Long myUserId, Long otherUserId) {
        this.myUserId = myUserId;
        this.otherUserId = otherUserId;
    }
}

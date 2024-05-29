package app.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "friends")
@IdClass(FriendId.class)
public class Friend {

    @Id
    @ManyToOne
    @JoinColumn(name = "my_user_id")
    private User myUserId;

    @Id
    @ManyToOne
    @JoinColumn(name = "other_user_id")
    private User otherUserId;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    public boolean equalsToOtherUser(User givenUser) {
        return givenUser.getId().equals(this.otherUserId.getId());
    }
}

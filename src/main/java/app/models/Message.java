package app.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public boolean validateMessage() {
        return this.message.length() > 255;
    }

    public boolean checkIfReceiverIsInFriendList() {
        List<Friend> friends = this.owner.getFriends();
        return friends.stream().anyMatch((e) -> e.equalsToOtherUser(this.receiver));
    }
}

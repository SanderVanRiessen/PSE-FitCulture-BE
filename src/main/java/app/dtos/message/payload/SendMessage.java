package app.dtos.message.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMessage {
    private String message;
    private Long receiverId;
}

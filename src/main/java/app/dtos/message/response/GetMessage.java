package app.dtos.message.response;

import app.dtos.user.response.GetUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMessage {
    private Long id;
    private String message;
    private GetUser owner;
    private GetUser receiver;
}

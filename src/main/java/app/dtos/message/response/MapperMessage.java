package app.dtos.message.response;

import app.dtos.user.response.GetUser;
import app.dtos.user.response.MapperUser;
import app.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperMessage {

    MapperUser mapperUser;

    @Autowired
    public void MapperUser(MapperUser mapperUser) {this.mapperUser = mapperUser;}

    public List<GetMessage> toPlainMessages(List<Message> messages){
        List<GetMessage> chat = new ArrayList<>();
        messages.forEach((e) -> {
            GetMessage message = toPlainMessage(e);
            chat.add(message);
        });
        return chat;
    }

    public GetMessage toPlainMessage(Message message){
        GetMessage dto = new GetMessage();
        GetUser owner = mapperUser.toPlainUser(message.getOwner());
        dto.setOwner(owner);

        GetUser receiver = mapperUser.toPlainUser(message.getReceiver());
        dto.setReceiver(receiver);

        dto.setMessage(message.getMessage());
        dto.setId(message.getId());
        return dto;
    }
}

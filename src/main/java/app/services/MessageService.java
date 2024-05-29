package app.services;

import app.dtos.message.response.GetMessage;
import app.dtos.message.response.MapperMessage;
import app.exceptions.MessageException;
import app.models.Message;
import app.models.User;
import app.repository.MessageRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;
    UserRepository userRepository;
    MapperMessage mapperMessage;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository, MapperMessage mapperMessage) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.mapperMessage = mapperMessage;
    }

    public GetMessage createMessage(User owner, User receiver, String textMessage) throws MessageException {
        Message message = new Message();
        message.setOwner(owner);
        message.setReceiver(receiver);
        message.setMessage(textMessage);

        if (message.validateMessage()) {
            throw new MessageException("Message too long");
        }

        if (!message.checkIfReceiverIsInFriendList()) {
            throw new MessageException("Given receiver is not in the friend list");
        }

        Message savedMessage = messageRepository.save(message);
        return mapperMessage.toPlainMessage(savedMessage);
    };

    public List<GetMessage> getMessagesWithSelectedFriend(User owner, User receiver) {
        List<Message> messages = messageRepository.getAllByOwnerAndReceiver(owner, receiver);
        return mapperMessage.toPlainMessages(messages);
    }
}

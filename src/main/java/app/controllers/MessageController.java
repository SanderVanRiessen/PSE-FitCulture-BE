package app.controllers;

import app.dtos.message.payload.SendMessage;
import app.dtos.message.response.GetMessage;
import app.dtos.message.response.MapperMessage;
import app.exceptions.MessageException;
import app.models.Message;
import app.models.User;
import app.repository.UserRepository;
import app.services.CustomUserDetailsService;
import app.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    MessageService messageService;
    CustomUserDetailsService customUserDetailsService;
    UserRepository userRepository;


    @Autowired
    public MessageController(CustomUserDetailsService customUserDetailsService, MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessage message) throws MessageException {
        User currentUser = customUserDetailsService.getCurrentUser();
        User receiverUser = userRepository.getById(message.getReceiverId());

        GetMessage createdMessage = messageService.createMessage(currentUser,receiverUser, message.getMessage());
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> getMessages(@PathVariable Long id){
        User currentUser = customUserDetailsService.getCurrentUser();
        User receiverUser = userRepository.getById(id);
        List<GetMessage> messages = messageService.getMessagesWithSelectedFriend(currentUser, receiverUser);
        return ResponseEntity.ok(messages);
    }
}

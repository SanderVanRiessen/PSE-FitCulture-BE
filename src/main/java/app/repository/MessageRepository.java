package app.repository;

import app.models.Message;
import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> getAllByOwnerAndReceiver(User owner, User receiver);
}

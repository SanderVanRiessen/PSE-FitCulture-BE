package app.repository;

import app.models.Friend;
import app.models.FriendId;
import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    Optional<Friend> findByMyUserIdAndOtherUserId(User myUserId, User otherUserId);

    Friend findByOtherUserId_Email(String otherUserId_email);
}

package app.Repository;

import app.Models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJpa extends AbstractEntityRepositoryJpa<User> {
    public Optional<Object> findByEmail(String username) {
    }
}

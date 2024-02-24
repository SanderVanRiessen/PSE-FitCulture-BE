package app.Repository;

import app.Models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJpa extends AbstractEntityRepositoryJpa<User> {
}

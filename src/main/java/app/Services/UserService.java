package app.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.Models.User;
import app.Repository.UserRepositoryJpa;

@Service
public class UserService {

    private final UserRepositoryJpa userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepositoryJpa userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String name, String email, String rawPassword, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        return userRepository.save(user);
    }
}

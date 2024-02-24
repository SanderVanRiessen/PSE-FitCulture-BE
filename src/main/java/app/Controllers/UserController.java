package app.Controllers;

import app.Models.User;
import app.Repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
public class UserController {

    @Autowired
    private UserRepositoryJpa userRepository;

    @Transactional
    @GetMapping("/users")
    public String getUsers() {
        return userRepository.findAll().toString();
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        System.out.print("User: " + user);
        User createdUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }
}

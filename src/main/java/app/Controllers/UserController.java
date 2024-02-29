package app.Controllers;

import app.Repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import app.Models.User;
import app.Services.UserService;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositoryJpa userRepository;

    @Transactional
    @GetMapping("/users")
    public String getUsers() {
        return userRepository.findAll().toString();
    }

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User createdUser = userService.createUser(user.getName(), user.getEmail(), user.getHashedPassword(), user.getRole());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }
}

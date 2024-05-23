package app.controllers;

import app.dtos.user.payload.MakeUserAuthor;
import app.dtos.user.payload.UserRegister;
import app.models.ERole;
import app.models.Role;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import app.models.User;
import app.services.UserService;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    UserService userService;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserController (UserService userService, UserRepository userRepository, RoleRepository roleRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/public/user")
    public ResponseEntity<?> getUser(@RequestParam(required = false) String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error: user not found with email: " + email));
        return ResponseEntity.ok(user);
    }

    @Transactional
    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegister user) {
        Role userRole = roleRepository.findByName(ERole.USER);
        System.out.print(user);
        User createdUser = userService.createUser(user.getName(), user.getEmail(), user.getPassword(), userRole);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @Transactional
    @PostMapping("/user/setauthor")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> makeUserAuthor(@RequestBody MakeUserAuthor userAuthor, Authentication auth) {
        User user = userRepository.findById(userAuthor.getId()).orElseThrow(()-> new RuntimeException("Error: user not found"));
        Role authorRole = roleRepository.findById(userAuthor.getRole()). orElseThrow(() -> new RuntimeException("Error: no role found"));
        user.setRole(authorRole);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    };
}

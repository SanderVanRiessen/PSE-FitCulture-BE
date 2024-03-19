package app.controllers;

import app.dtos.user.payload.MakeUserAuthor;
import app.models.ERole;
import app.models.Role;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import app.models.User;
import app.services.UserService;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

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

    @Transactional
    @GetMapping("/users")
    public String getUsers() {
        return userRepository.findAll().toString();
    }

    @Transactional
    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Role userRole = roleRepository.findByName(ERole.USER);
        User createdUser = userService.createUser(user.getName(), user.getEmail(), user.getPassword(), userRole);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @Transactional
    @PostMapping("/user/setauthor")
    public ResponseEntity<?> makeUserAuthor(@RequestBody MakeUserAuthor userAuthor, Authentication auth) {
        UserDetails userDetails = (UserDetails)  auth.getPrincipal();
        System.out.print(userDetails);
        User user = userRepository.findById(userAuthor.getId()).orElseThrow(()-> new RuntimeException("Error: user not found"));
        Role authorRole = roleRepository.findByName(ERole.AUTHOR);
        user.setRole(authorRole);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    };
}

package app.seed;

import app.models.Role;
import app.models.User;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedAuthor {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SeedAuthor(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository =  roleRepository;
    }

    @Bean
    public CommandLineRunner seedAuthorInDataBase() {
        return args -> {
            boolean foundUser = userRepository.existsByEmail("author@author.nl");
            Role authorRole = roleRepository.findById(3L).orElseThrow(() -> new RuntimeException("no roles"));

            if (!foundUser){
                User adminUser = new User();
                adminUser.setEmail("author@author.nl");
                adminUser.setName("Author");
                adminUser.setPassword(passwordEncoder.encode("Author123!"));
                adminUser.setRole(authorRole);
                userRepository.save(adminUser);
            }
        };
    }
}


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
public class SeedAdmin {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public SeedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository =  roleRepository;
    }

    @Bean
    public CommandLineRunner seedAdminInDataBase() {
        return args -> {
            boolean foundUser = userRepository.existsByEmail("admin@admin.nl");
            Role adminRole = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("no roles"));

            if (!foundUser){
                User adminUser = new User();
                adminUser.setEmail("admin@admin.nl");
                adminUser.setName("Admin");
                adminUser.setPassword(passwordEncoder.encode("Admin123!"));
                adminUser.setRole(adminRole);
                userRepository.save(adminUser);
            }
        };
    }
}


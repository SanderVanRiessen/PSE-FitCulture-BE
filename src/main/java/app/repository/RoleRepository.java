package app.repository;

import app.models.ERole;
import app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}

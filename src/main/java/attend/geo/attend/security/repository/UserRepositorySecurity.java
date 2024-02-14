package attend.geo.attend.security.repository;

import attend.geo.attend.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositorySecurity extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}

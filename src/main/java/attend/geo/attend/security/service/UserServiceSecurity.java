package attend.geo.attend.security.service;

import attend.geo.attend.security.domain.User;
import attend.geo.attend.security.repository.UserRepositorySecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceSecurity {
    private final UserRepositorySecurity userRepository;

    private final PasswordEncoder encoder;

    public UserServiceSecurity(UserRepositorySecurity userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User save(User user) {
        String password = encoder.encode(user.getPassword());

        user.setPassword(password);
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}

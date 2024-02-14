package attend.geo.attend.security.web;

import attend.geo.attend.security.domain.User;
import attend.geo.attend.security.service.UserServiceSecurity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserServiceSecurity userService;

    public UserResource(UserServiceSecurity userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody User user) {
        User result = userService.save(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}

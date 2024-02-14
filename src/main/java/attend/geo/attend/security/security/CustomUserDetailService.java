package attend.geo.attend.security.security;

import attend.geo.attend.security.domain.Authority;
import attend.geo.attend.security.repository.UserRepositorySecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepositorySecurity userRepository;

    public CustomUserDetailService(UserRepositorySecurity userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerCaseUsername = username.toLowerCase();
        return userRepository
                .findByUsername(lowerCaseUsername)
                .map(user -> createSpringSecurityUser(username, user))
                .orElseThrow(() -> new UserNotActivateException("User " + username + " was not found in the database"));

    }
    private User createSpringSecurityUser(String username, attend.geo.attend.security.domain.User user) {
        if (!user.isActivated()) {
            throw new UserNotActivateException("User " + username + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, user.getPassword(), grantedAuthorities);
    }
}

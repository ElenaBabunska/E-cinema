package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
    User create(String username, String password, String name, String surname, Role role);
    List<User> findAll();
    User findByUsername(String username);
    void addUserToSubscription(Long subscriptionId, String username);
    User save(User user);
}

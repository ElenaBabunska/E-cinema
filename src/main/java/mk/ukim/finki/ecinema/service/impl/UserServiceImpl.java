package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Subscription;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.enumerations.Role;
import mk.ukim.finki.ecinema.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.ecinema.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.ecinema.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.ecinema.repository.SubscriptionRepository;
import mk.ukim.finki.ecinema.repository.UserRepository;
import mk.ukim.finki.ecinema.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionRepository subscriptionRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username,passwordEncoder.encode(password),name,surname,userRole);
        return userRepository.save(user);
    }

    @Override
    public User create(String username, String password, String name, String surname, Role role) {
        User user = new User(username, password, name, surname, role);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void addUserToSubscription(Long subscriptionId, String username) {
        User user = this.findByUsername(username);
        Optional<Subscription> subscription = this.subscriptionRepository.findById(subscriptionId);
        subscription.get().setStart(LocalDateTime.now());
        if(subscription.get().getName().equals("Monthly Plan")){
            subscription.get().setEnd(LocalDateTime.now().plusMonths(1));
        }
        else if(subscription.get().getName().equals("Annual Plan")){
            subscription.get().setEnd(LocalDateTime.now().plusMonths(12));
        }
        user.setSubscription(subscription.get());
        this.userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }


}

package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Subscription;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.exceptions.SubscriptionIdNotFoundException;
import mk.ukim.finki.ecinema.model.exceptions.UserNotFoundException;
import mk.ukim.finki.ecinema.repository.SubscriptionRepository;
import mk.ukim.finki.ecinema.repository.UserRepository;
import mk.ukim.finki.ecinema.service.SubscriptionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Subscription> findAll() {
        return this.subscriptionRepository.findAll();
    }

    @Override
    public Subscription findById(Long id) {
        return this.subscriptionRepository.findById(id).orElseThrow(()-> new SubscriptionIdNotFoundException(id));
    }

    @Override
    public Subscription save(String name, String description, Double price, String url) {
        Subscription subscription = new Subscription(name, description, price, url);
        return this.subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription update(Long id, String name, String description, Double price) {
        Subscription subscription = this.findById(id);
        subscription.setName(name);
        subscription.setDescription(description);
        subscription.setPrice(price);

        return this.subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteById(Long id) {
        this.subscriptionRepository.deleteById(id);
    }

//    @Override
//    public void addUserToPlatform(Long id, String username) {
//        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
//        Subscription subscription = this.findById(id);
//        subscription.getUsers().add(user);
//    }

    @Override
    public List<User> listUsersInProgram(Long subscriptionId) {
        Subscription subscription = this.findById(subscriptionId);
        List<User> users= this.userRepository.findAll();
        List<User> usersInSubscription = new ArrayList<>();
        for(User u:users){
            if(u.getSubscription()!=null && u.getSubscription().getName().equals(subscription.getName())){
                usersInSubscription.add(u);
            }
        }
        return usersInSubscription;
    }
}

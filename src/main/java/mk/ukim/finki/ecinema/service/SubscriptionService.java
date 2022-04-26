package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.Subscription;
import mk.ukim.finki.ecinema.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionService {
    List<Subscription> findAll();
    Subscription findById(Long id);
    Subscription save(String name, String description, Double price, String url);
    Subscription update(Long id, String name, String description, Double price);
    void deleteById(Long id);
//    void addUserToPlatform(Long id, String username);
    List<User> listUsersInProgram(Long subscriptionId);
}

package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.User;

public interface AuthService {
    User login(String username, String password);
}

package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

//koga prv pat se avtenticiraat so fb nemaat tie acc vo nasata aplikacija. I ovde go kreirame userot
@Service
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        User user = new User();
        user.setUsername(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        userRepository.save(user);
        return user.getUsername();
    }
}
package mk.ukim.finki.ecinema.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SubscriptionIdNotFoundException extends RuntimeException{
    public SubscriptionIdNotFoundException(Long id) {
        super(String.format("Subscription with id: %d is not found", id));
    }
}
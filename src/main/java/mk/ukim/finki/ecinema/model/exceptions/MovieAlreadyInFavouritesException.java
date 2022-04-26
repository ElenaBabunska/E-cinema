package mk.ukim.finki.ecinema.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MovieAlreadyInFavouritesException extends RuntimeException{
    public MovieAlreadyInFavouritesException(Long id, String username) {
        super(String.format("Movie with id: %d already exists in favorites for user with email %s", id, username));
    }
}

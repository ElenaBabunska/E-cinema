package mk.ukim.finki.ecinema.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FavouritesNotFoundException extends RuntimeException{
    public FavouritesNotFoundException(Long id){
        super(String.format("Favourite with id %d does not exists",id));
    }
}
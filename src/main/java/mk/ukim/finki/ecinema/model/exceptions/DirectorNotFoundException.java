package mk.ukim.finki.ecinema.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DirectorNotFoundException extends RuntimeException{
    public DirectorNotFoundException(Long id) {
        super(String.format("Director with id: %d is not found", id));
    }
}


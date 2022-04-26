package mk.ukim.finki.ecinema.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(Long id) {
        super(String.format("Ticket with id: %d is not found", id));
    }
}

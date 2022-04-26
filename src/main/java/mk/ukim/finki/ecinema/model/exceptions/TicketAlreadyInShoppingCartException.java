package mk.ukim.finki.ecinema.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class TicketAlreadyInShoppingCartException extends RuntimeException{
    public TicketAlreadyInShoppingCartException(Long ticketId, String username) {
        super(String.format("Ticket with id: %d already exists in shopping cart for user with username %s", ticketId, username));
    }
}

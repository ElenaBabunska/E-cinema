package mk.ukim.finki.ecinema.model.exceptions;

public class CouldNotDeleteSubscriptionException extends RuntimeException{
    public CouldNotDeleteSubscriptionException() {
        super(String.format("You can't delete this subscription, because there are subscribed users!"));
    }
}

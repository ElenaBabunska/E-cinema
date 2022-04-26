package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.ShoppingCart;
import mk.ukim.finki.ecinema.model.Ticket;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.ecinema.model.exceptions.*;
import mk.ukim.finki.ecinema.repository.ShoppingCartRepository;
import mk.ukim.finki.ecinema.repository.TicketRepository;
import mk.ukim.finki.ecinema.repository.UserRepository;
import mk.ukim.finki.ecinema.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> listAllTicketsInShoppingCart(Long cartId) {
        if (!this.shoppingCartRepository.findById(cartId).isPresent()){
            throw new ShoppingCartNotFoundException(cartId);
        }
        return this.shoppingCartRepository.findById(cartId).get().getTickets();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addTicketToShoppingCart(String username, Long ticketId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));

        if(shoppingCart.getTickets()
                .stream().filter(i -> i.getCode().equals(ticketId))
                .collect(Collectors.toList()).size() > 0)
            throw new TicketAlreadyInShoppingCartException(ticketId, username);
        shoppingCart.getTickets().add(ticket);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart deleteTicketFromShoppingCart(String username, Long ticketId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));

        if(shoppingCart.getTickets()
                .stream().filter(i -> i.getCode().equals(ticketId))
                .collect(Collectors.toList()).size() <= 0)
            throw new TicketNotFoundInShoppingCartException(ticketId, username);
        shoppingCart.getTickets().remove(ticket);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}

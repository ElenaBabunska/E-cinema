package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.ShoppingCart;
import mk.ukim.finki.ecinema.model.Ticket;

import java.util.List;

public interface ShoppingCartService {
    List<Ticket> listAllTicketsInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addTicketToShoppingCart(String username, Long ticketId);
    ShoppingCart deleteTicketFromShoppingCart(String username, Long ticketId);
}

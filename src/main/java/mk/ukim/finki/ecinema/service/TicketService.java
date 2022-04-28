package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<Ticket> findAll();
    Optional<Ticket> findById(Long id);
    Ticket save(Double price, Long movieId);
    Ticket update(Long code, Double price);
    Ticket update2(Long code, Double price);
    void deleteById(Long code);
}

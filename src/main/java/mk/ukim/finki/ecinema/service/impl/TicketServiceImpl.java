package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Movie;
import mk.ukim.finki.ecinema.model.Ticket;
import mk.ukim.finki.ecinema.model.exceptions.MovieNotFoundException;
import mk.ukim.finki.ecinema.model.exceptions.TicketNotFoundException;
import mk.ukim.finki.ecinema.repository.MovieRepository;
import mk.ukim.finki.ecinema.repository.TicketRepository;
import mk.ukim.finki.ecinema.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, MovieRepository movieRepository) {
        this.ticketRepository = ticketRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return this.ticketRepository.findById(id);
    }

    @Override
    public Ticket save(Double price, Long movieId) {
        Movie movie = this.movieRepository.getById(movieId);
        return this.ticketRepository.save(new Ticket(price, movie));
    }

    @Override
    public Ticket update(Long code, Double price, Long movieId) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        Ticket ticket = this.ticketRepository.findById(code).orElseThrow(()-> new TicketNotFoundException(code));
        ticket.setPrice(price);
        ticket.setMovie(movie);
        return this.ticketRepository.save(ticket);
    }

    @Override
    public Ticket update2(Long code, Double price) {
        Ticket ticket = this.ticketRepository.findById(code).orElseThrow(() -> new TicketNotFoundException(code));
        ticket.setDiscountPrice(price);
        return this.ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(Long code) {
        this.ticketRepository.deleteById(code);
    }
}

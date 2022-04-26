package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();

    Movie findById(Long id);

    Optional<Movie> findByName(String name);

    Movie create(String name, String description, Director director, Category category, LocalDateTime premiereStart, LocalDateTime premiereEnd, List<Ticket> tickets, String url);

    Movie update(Long id, String name, String description, Director director, Category category, LocalDateTime premiereStart, LocalDateTime premiereEnd, List<Ticket> tickets, String url);

    void deleteById(Long id);

    List<Movie> filter(String name, Long categoryId);
}

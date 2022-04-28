package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();

    Movie findById(Long id);

    Optional<Movie> findByName(String name);

    Movie create(String name, String description, Director director, Category category, LocalDate premiereStart, LocalDate premiereEnd, List<Ticket> tickets, String url);

    Movie update(Long id, String name, String description, Director director, Category category, LocalDate premiereStart, LocalDate premiereEnd, List<Ticket> tickets, String url);

    void deleteById(Long id);

    List<Movie> filter(String name, Long categoryId);
}

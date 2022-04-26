package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.*;
import mk.ukim.finki.ecinema.model.exceptions.CategoryIdNotFoundException;
import mk.ukim.finki.ecinema.model.exceptions.DirectorNotFoundException;
import mk.ukim.finki.ecinema.model.exceptions.MovieNotFoundException;
import mk.ukim.finki.ecinema.repository.CategoryRepository;
import mk.ukim.finki.ecinema.repository.DirectorRepository;
import mk.ukim.finki.ecinema.repository.MovieRepository;
import mk.ukim.finki.ecinema.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final CategoryRepository categoryRepository;

    public MovieServiceImpl(MovieRepository movieRepository, DirectorRepository directorRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Movie> findAll() {
        return this.movieRepository.findAll();
    }

    @Override
    public Movie findById(Long id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public Optional<Movie> findByName(String name) {
        return this.movieRepository.findByName(name);
    }


    @Override
    public Movie create(String name, String description, Director director, Category category, LocalDateTime premiereStart, LocalDateTime premiereEnd, List<Ticket> tickets, String url) {
        Movie movie = new Movie(name, description, director, category, premiereStart, premiereEnd, tickets, url);

        return this.movieRepository.save(movie);
    }

    @Override
    public Movie update(Long id, String name, String description, Director director, Category category, LocalDateTime premiereStart, LocalDateTime premiereEnd, List<Ticket> tickets, String url) {
        Movie movie = this.findById(id);
        movie.setName(name);
        movie.setDescription(description);
        movie.setPremiereStart(premiereStart);
        movie.setPremiereEnd(premiereEnd);
        movie.setTickets(tickets);
        movie.setUrl(url);
        movie.setDirector(director);
        movie.setCategory(category);

        return this.movieRepository.save(movie);
    }

    @Override
    public void deleteById(Long id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> filter(String name, Long categoryId) {
        name= name.toLowerCase();
        String name1 = '%' + name + '%';
        name1 = name1.toLowerCase();

        Category cat = categoryId != null ? this.categoryRepository.findById(categoryId).orElse((Category)null):null;
        if(name != null && categoryId!= null){
            return this.movieRepository.findAllByNameLikeAndCategory(name1,cat);
        }
        else if(name!=null){
            return this.movieRepository.findAllByNameLike(name1);
        }
        else if(categoryId != null){
            return this.movieRepository.findAllByCategory(cat);
        }
        else {
            return this.findAll();
        }
    }
}


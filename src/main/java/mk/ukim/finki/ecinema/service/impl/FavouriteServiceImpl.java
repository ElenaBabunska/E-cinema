package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Favourite;
import mk.ukim.finki.ecinema.model.Movie;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.exceptions.FavouritesNotFoundException;
import mk.ukim.finki.ecinema.model.exceptions.MovieAlreadyInFavouritesException;
import mk.ukim.finki.ecinema.model.exceptions.MovieNotFoundException;
import mk.ukim.finki.ecinema.repository.FavouriteRepository;
import mk.ukim.finki.ecinema.repository.MovieRepository;
import mk.ukim.finki.ecinema.repository.UserRepository;
import mk.ukim.finki.ecinema.service.FavouriteService;
import mk.ukim.finki.ecinema.service.MovieService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final MovieService movieService;
    private final MovieRepository movieRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, UserRepository userRepository, MovieService movieService, MovieRepository movieRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> listAllFavouriteMovie(Long favId) {
        if (!this.favouriteRepository.findById(favId).isPresent()){
            throw new FavouritesNotFoundException(favId);
        }
        return this.favouriteRepository.findById(favId).get().getMovies();
    }

    @Override
    public Optional<Favourite> findById(Long favId) {
        return Optional.of(this.favouriteRepository.findById(favId).orElseThrow(() ->new FavouritesNotFoundException(favId)));
    }

    @Override
    public Favourite getActiveFavourite(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return this.favouriteRepository
                .findByUser(user)
                .orElseGet(() -> {
                    Favourite favourite = new Favourite(user);
                    return this.favouriteRepository.save(favourite);
                });
    }

    @Override
    public Favourite addMovieToFavourite(String username, Long movieId) {
        Favourite favourite = this.getActiveFavourite(username);
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));

        if (favourite.getMovies() != null && favourite.getMovies()
                .stream().filter(m -> m.getId().equals(movieId)).collect(Collectors.toList()).size() > 0){
            throw new MovieAlreadyInFavouritesException(movieId, username);
        }
        favourite.getMovies().add(movie);
        return this.favouriteRepository.save(favourite);
    }

    @Override
    public Optional<Movie> delete(Long movieId, String username) {
        Favourite favourite = this.getActiveFavourite(username);
        Optional<Movie> movie = favourite.getMovies().stream().filter(m->m.getId().equals(movieId)).findAny();
        movie.ifPresent(value -> favourite.getMovies().remove(value));
        this.favouriteRepository.save(favourite);

        return movie;
    }

}

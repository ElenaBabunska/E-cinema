package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.Favourite;
import mk.ukim.finki.ecinema.model.Movie;

import java.util.List;
import java.util.Optional;

public interface FavouriteService {
    List<Movie> listAllFavouriteMovie(Long favId);
    Optional<Favourite> findById(Long favId);
    Favourite getActiveFavourite(String username);
    Favourite addMovieToFavourite(String username, Long movieId);
    Optional<Movie> delete(Long movieId, String username);

}

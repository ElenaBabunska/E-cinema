package mk.ukim.finki.ecinema.repository;

import mk.ukim.finki.ecinema.model.Favourite;
import mk.ukim.finki.ecinema.model.Movie;
import mk.ukim.finki.ecinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Movie> findAllById(Long movieId);
    Optional<Favourite> findByUser(User user);
}

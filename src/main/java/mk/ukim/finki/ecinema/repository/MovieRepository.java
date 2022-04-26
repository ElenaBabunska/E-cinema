package mk.ukim.finki.ecinema.repository;


import mk.ukim.finki.ecinema.model.Category;
import mk.ukim.finki.ecinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);
    List<Movie> findAllByNameLike(String name);
    List<Movie> findAllByNameLikeAndCategory(String name, Category category);
    List<Movie> findAllByCategory(Category category);
}

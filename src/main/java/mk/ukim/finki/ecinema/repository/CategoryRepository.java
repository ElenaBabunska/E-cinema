package mk.ukim.finki.ecinema.repository;

import mk.ukim.finki.ecinema.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNameLike(String name);
}

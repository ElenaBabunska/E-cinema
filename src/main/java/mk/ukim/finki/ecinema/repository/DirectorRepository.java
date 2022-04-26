package mk.ukim.finki.ecinema.repository;

import mk.ukim.finki.ecinema.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
}

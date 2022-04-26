package mk.ukim.finki.ecinema.repository;


import mk.ukim.finki.ecinema.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}

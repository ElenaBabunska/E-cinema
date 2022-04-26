package mk.ukim.finki.ecinema.repository;

import mk.ukim.finki.ecinema.model.ShoppingCart;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}

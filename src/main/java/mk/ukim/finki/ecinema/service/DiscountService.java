package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.Discount;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscountService {

    List<Discount> findAll();

    Discount findById(Long id);

    Optional<Discount> create(LocalDate validUntil, String name, Double value);

    Optional<Discount> update(Long id, LocalDate validUntil, String name, Double value);

    void deleteById(Long id);

    Optional<Discount> assignDiscount(String username, Long discountId);
}

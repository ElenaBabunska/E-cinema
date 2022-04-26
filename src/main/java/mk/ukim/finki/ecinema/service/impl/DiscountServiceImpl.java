package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Discount;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.exceptions.DiscountNotFoundException;
import mk.ukim.finki.ecinema.model.exceptions.UserNotFoundException;
import mk.ukim.finki.ecinema.repository.DiscountRepository;
import mk.ukim.finki.ecinema.repository.UserRepository;
import mk.ukim.finki.ecinema.service.DiscountService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, UserRepository userRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Discount> findAll() {
        return this.discountRepository.findAll();
    }

    @Override
    public Discount findById(Long id) {
        return this.discountRepository.findById(id).orElseThrow(()-> new DiscountNotFoundException(id));
    }

    @Override
    public Optional<Discount> create(LocalDate validUntil, String name, Double value) {
        return Optional.of(this.discountRepository.save(new Discount(validUntil, name, value)));
    }

    @Override
    public Optional<Discount> update(Long id,LocalDate validUntil, String name, Double value) {
        Discount discount = this.discountRepository.findById(id)
                .orElseThrow(() -> new DiscountNotFoundException(id));
        discount.setValidUntil(validUntil);
        discount.setName(name);
        discount.setValue(value);
        return Optional.of(this.discountRepository.save(discount));
    }

    @Override
    public void deleteById(Long id) {
        this.discountRepository.deleteById(id);
    }

    @Override
    public Optional<Discount> assignDiscount(String username, Long discountId) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Discount discount = this.discountRepository.findById(discountId)
                .orElseThrow(() -> new DiscountNotFoundException(discountId));

        discount.getUsers().add(user);
        return Optional.of(this.discountRepository.save(discount));
    }
}

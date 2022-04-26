package mk.ukim.finki.ecinema.service;

import mk.ukim.finki.ecinema.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {
    List<Director> findAll();
    Optional<Director> findById(Long embg);
    Optional<Director> save(String name, String surname, String eMail);
    Optional<Director> update(Long embg, String name, String surname, String eMail);
    void deleteById(Long embg);
}

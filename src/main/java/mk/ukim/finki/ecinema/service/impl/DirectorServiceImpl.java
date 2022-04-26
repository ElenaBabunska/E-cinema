package mk.ukim.finki.ecinema.service.impl;

import mk.ukim.finki.ecinema.model.Director;
import mk.ukim.finki.ecinema.model.exceptions.DirectorNotFoundException;
import mk.ukim.finki.ecinema.repository.DirectorRepository;
import mk.ukim.finki.ecinema.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public List<Director> findAll() {
        return this.directorRepository.findAll();
    }
    @Override
    public Optional<Director> findById(Long embg) {
        return this.directorRepository.findById(embg);
    }

    @Override
    public Optional<Director> save(String name, String surname, String eMail) {
        return Optional.of(this.directorRepository.save(new Director(name, surname, eMail)));
    }

    @Override
    public Optional<Director> update(Long embg, String name, String surname, String eMail) {
        Director director = this.directorRepository.findById(embg).orElseThrow(()->new DirectorNotFoundException(embg));
        director.setName(name);
        director.setSurname(surname);
        director.setEMail(eMail);
        return Optional.of(this.directorRepository.save(director));
    }

    @Override
    public void deleteById(Long embg) {
        this.directorRepository.deleteById(embg);
    }


}

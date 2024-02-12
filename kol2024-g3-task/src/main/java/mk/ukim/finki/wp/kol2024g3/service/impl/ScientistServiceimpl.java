package mk.ukim.finki.wp.kol2024g3.service.impl;


import mk.ukim.finki.wp.kol2024g3.model.Scientist;
import mk.ukim.finki.wp.kol2024g3.model.exceptions.InvalidScientistIdException;
import mk.ukim.finki.wp.kol2024g3.repository.PresentationRepository;
import mk.ukim.finki.wp.kol2024g3.repository.ScientistRepository;
import mk.ukim.finki.wp.kol2024g3.service.ScientistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientistServiceimpl  implements ScientistService {
    private final ScientistRepository scientistRepository;

    public ScientistServiceimpl( ScientistRepository scientistRepository) {
        this.scientistRepository = scientistRepository;
    }

    @Override
    public Scientist findById(Long id) {
        return scientistRepository.findById(id).orElseThrow(InvalidScientistIdException::new);
    }

    @Override
    public List<Scientist> listAll() {
        return scientistRepository.findAll();
    }

    @Override
    public Scientist create(String name) {

        Scientist scientist= new Scientist(name);
        return scientistRepository.save(scientist);

    }
}

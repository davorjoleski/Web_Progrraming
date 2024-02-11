package mk.ukim.finki.wp.kol2024g2.service.impl;


import mk.ukim.finki.wp.kol2024g2.model.Scientist;
import mk.ukim.finki.wp.kol2024g2.model.exceptions.InvalidScientistIdException;
import mk.ukim.finki.wp.kol2024g2.repository.ScientistRepository;
import mk.ukim.finki.wp.kol2024g2.service.ScientistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScinetistServiceImpl implements ScientistService {

    private  final ScientistRepository scientistRepository;

    public ScinetistServiceImpl(ScientistRepository scientistRepository) {
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
        Scientist scientist1 = new Scientist(name);
          scientistRepository.save(scientist1);

        return scientist1;

    }
}

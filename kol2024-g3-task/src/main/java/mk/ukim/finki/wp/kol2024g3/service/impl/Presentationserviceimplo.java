package mk.ukim.finki.wp.kol2024g3.service.impl;


import mk.ukim.finki.wp.kol2024g3.model.Presentation;
import mk.ukim.finki.wp.kol2024g3.model.PresentationType;
import mk.ukim.finki.wp.kol2024g3.model.Scientist;
import mk.ukim.finki.wp.kol2024g3.model.exceptions.InvalidPresentationIdException;
import mk.ukim.finki.wp.kol2024g3.repository.PresentationRepository;
import mk.ukim.finki.wp.kol2024g3.service.PresentationService;
import mk.ukim.finki.wp.kol2024g3.service.ScientistService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Presentationserviceimplo implements PresentationService {

    private final PresentationRepository presentationRepository;
    private final ScientistService scientistService;


    public Presentationserviceimplo(PresentationRepository presentationRepository, ScientistService scientistService) {
        this.presentationRepository = presentationRepository;
        this.scientistService = scientistService;
    }

    @Override
    public List<Presentation> listAll() {
        return presentationRepository.findAll();
    }

    @Override
    public Presentation findById(Long id) {
        return presentationRepository.findById(id).orElseThrow(InvalidPresentationIdException::new);
    }

    @Override
    public Presentation create(String name, String description, LocalDate datePresented, PresentationType presentationType, Long scientist) {
        Scientist pres = scientistService.findById(scientist);
        Presentation presentation = new Presentation(name, description, datePresented, presentationType, pres);

        presentationRepository.save(presentation);
        return presentation;
    }

    @Override
    public Presentation update(Long id, String name, String description, LocalDate datePresented, PresentationType presentationType, Long scientist) {
        Scientist pres = scientistService.findById(scientist);
        Presentation presentation = new Presentation(name, description, datePresented, presentationType, pres);
        presentation.setName(name);
        presentation.setId(id);


        presentation.setDescription(description);
        presentation.setDatePresented(datePresented);

        presentation.setPresentationType(presentationType);
        presentation.setScientist(pres);
        presentationRepository.save(presentation);

        return presentation;

    }

    @Override
    public Presentation delete(Long id) {

        Presentation pres = findById(id);
        presentationRepository.delete(pres);

        return pres;

    }

    @Override
    public Presentation vote(Long id) {
        Presentation player = findById(id);
        player.setVotes(player.getVotes() + 1);
        presentationRepository.save(player);

        return player;
    }

    @Override
    public List<Presentation> filterPresentations(Integer yearsMoreThan, PresentationType presentationType) {

        if (yearsMoreThan == null && presentationType == null) {
            return presentationRepository.findAll();
        }
        if (yearsMoreThan == null) {
            return presentationRepository.findByPresentationType(presentationType);

        }
        if (presentationType == null) {

            LocalDate years = LocalDate.now().minusYears(yearsMoreThan);
            return presentationRepository.findByDatePresentedBefore(years);

        }
        LocalDate years = LocalDate.now().minusYears(yearsMoreThan);
        return presentationRepository.findByDatePresentedBeforeAndPresentationType(years, presentationType);


    }
}

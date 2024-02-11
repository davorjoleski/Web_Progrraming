package mk.ukim.finki.wp.kol2024g2.service.impl;


import mk.ukim.finki.wp.kol2024g2.model.Application;
import mk.ukim.finki.wp.kol2024g2.model.ApplicationType;
import mk.ukim.finki.wp.kol2024g2.model.Scientist;
import mk.ukim.finki.wp.kol2024g2.model.exceptions.InvalidApplicationIdException;
import mk.ukim.finki.wp.kol2024g2.repository.ApplicationRepository;
import mk.ukim.finki.wp.kol2024g2.service.ApplicationService;
import mk.ukim.finki.wp.kol2024g2.service.ScientistService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ScientistService scientistService;

    public AppServiceImpl(ApplicationRepository applicationRepository, ScientistService scientistService) {
        this.applicationRepository = applicationRepository;
        this.scientistService = scientistService;
    }

    @Override
    public List<Application> listAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Application findById(Long id) {
        return applicationRepository.findById(id).orElseThrow(InvalidApplicationIdException::new);
    }

    @Override
    public Application create(String name, String originStory, LocalDate dateCreated, ApplicationType applicationType, Long scientist) {

        Scientist scientist1 = scientistService.findById(scientist);
        Application app = new Application(name, originStory, dateCreated, applicationType, scientist1);
        applicationRepository.save(app);
        return app;

    }

    @Override
    public Application update(Long id, String name, String originStory, LocalDate dateCreated, ApplicationType applicationType, Long scientist) {
        Scientist scientist1 = scientistService.findById(scientist);
        Application app = new Application(name, originStory, dateCreated, applicationType, scientist1);
        app.setId(id);
        app.setDateCreated(dateCreated);
        app.setApplicationType(applicationType);
        app.setScientist(scientist1);
        app.setName(name);
        app.setOriginStory(originStory);

        applicationRepository.save(app);

        return app;


    }

    @Override
    public Application delete(Long id) {
        Application app = findById(id);
        applicationRepository.delete(app);
        return app;


    }

    @Override
    public Application vote(Long id) {
        Application app = findById(id);
        app.setVotes(app.getVotes() + 1);
        applicationRepository.save(app);

        return app;


    }
     //point
    @Override
    public List<Application> filterApplications(Integer yearsMoreThan, ApplicationType applicationType) {
        if(yearsMoreThan==null && applicationType==null){
            return applicationRepository.findAll();
        }
        if(yearsMoreThan==null){
            return applicationRepository.findByApplicationType(applicationType);
        }

        if(applicationType==null){
            LocalDate dateYearsAgo = LocalDate.now().minusYears(yearsMoreThan);
            return applicationRepository.findByDateCreatedBefore(dateYearsAgo);
        }
        LocalDate dateYearsAgo = LocalDate.now().minusYears(yearsMoreThan);

        return  applicationRepository.findByDateCreatedBeforeAndAndApplicationType(dateYearsAgo,applicationType);



    }
}

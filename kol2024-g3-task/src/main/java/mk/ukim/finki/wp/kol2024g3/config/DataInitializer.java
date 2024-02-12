package mk.ukim.finki.wp.kol2024g3.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.kol2024g3.model.PresentationType;
import mk.ukim.finki.wp.kol2024g3.service.PresentationService;
import mk.ukim.finki.wp.kol2024g3.service.ScientistService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataInitializer {

    private final ScientistService scientistService;
    private final PresentationService presentationService;

    public DataInitializer(ScientistService scientistService, PresentationService presentationService) {
        this.scientistService = scientistService;
        this.presentationService = presentationService;
    }

    private PresentationType randomize(int i) {
        if (i % 2 == 0) return PresentationType.TECHNOLOGY;
        return PresentationType.SOCIAL;
    }
@PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.scientistService.create("Scientist: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.presentationService.create("Presentation: " + i, "Description: " + i, LocalDate.now().minusYears(25 + i), this.randomize(i), this.scientistService.listAll().get((i - 1) % 5).getId());
        }
    }
}

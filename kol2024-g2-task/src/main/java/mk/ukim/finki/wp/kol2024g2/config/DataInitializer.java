package mk.ukim.finki.wp.kol2024g2.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.kol2024g2.model.ApplicationType;
import mk.ukim.finki.wp.kol2024g2.service.ApplicationService;
import mk.ukim.finki.wp.kol2024g2.service.ScientistService;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    private final ScientistService scientistService;
    private final ApplicationService applicationService;

    public DataInitializer(ScientistService scientistService, ApplicationService applicationService) {
        this.scientistService = scientistService;
        this.applicationService = applicationService;
    }

    private ApplicationType randomize(int i) {
        if (i % 2 == 0) return ApplicationType.TECHNOLOGY;
        return ApplicationType.SOCIAL;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.scientistService.create("Scientist: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.applicationService.create("Application: " + i, "Origin Story: " + i, LocalDate.now().minusYears(25 + i), this.randomize(i), this.scientistService.listAll().get((i - 1) % 5).getId());
        }
    }
}

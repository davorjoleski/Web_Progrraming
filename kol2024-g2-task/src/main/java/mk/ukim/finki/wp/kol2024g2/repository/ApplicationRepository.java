package mk.ukim.finki.wp.kol2024g2.repository;

import mk.ukim.finki.wp.kol2024g2.model.Application;
import mk.ukim.finki.wp.kol2024g2.model.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application>findByDateCreatedBeforeAndAndApplicationType(LocalDate localDate, ApplicationType applicationType);
    List<Application>findByDateCreatedBefore(LocalDate localDate);
    List<Application>findByApplicationType(ApplicationType applicationType);


}

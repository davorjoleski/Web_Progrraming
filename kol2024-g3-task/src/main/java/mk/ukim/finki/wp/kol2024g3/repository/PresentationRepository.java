package mk.ukim.finki.wp.kol2024g3.repository;

import mk.ukim.finki.wp.kol2024g3.model.Presentation;
import mk.ukim.finki.wp.kol2024g3.model.PresentationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PresentationRepository extends JpaRepository<Presentation,Long> {

    List<Presentation> findByDatePresentedBeforeAndPresentationType(LocalDate localDate, PresentationType presentationType);
    List<Presentation> findByDatePresentedBefore(LocalDate localDate);
    List<Presentation> findByPresentationType( PresentationType presentationType);

}

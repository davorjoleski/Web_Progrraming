package mk.ukim.finki.wp.kol2024g2.repository;

import mk.ukim.finki.wp.kol2024g2.model.Scientist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScientistRepository  extends JpaRepository<Scientist,Long> {
}

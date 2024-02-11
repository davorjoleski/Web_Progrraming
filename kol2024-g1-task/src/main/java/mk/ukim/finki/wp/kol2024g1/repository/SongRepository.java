package mk.ukim.finki.wp.kol2024g1.repository;

import mk.ukim.finki.wp.kol2024g1.model.Artist;
import mk.ukim.finki.wp.kol2024g1.model.Genre;
import mk.ukim.finki.wp.kol2024g1.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long> {


    List<Song> findByDateCreatedBeforeAndGenre(LocalDate date, Genre genre);

    List<Song> findSongsByGenre(Genre genre);
    List<Song>  findByDateCreatedBefore(LocalDate localDate);

}

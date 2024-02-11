package mk.ukim.finki.wp.kol2024g1.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.kol2024g1.model.Genre;
import mk.ukim.finki.wp.kol2024g1.service.SongService;
import mk.ukim.finki.wp.kol2024g1.service.ArtistService;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    private final ArtistService artistService;
    private final SongService songService;

    public DataInitializer(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    private Genre randomize(int i) {
        if (i % 2 == 0) return Genre.POP;
        return Genre.ROCK;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.artistService.create("Artist: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.songService.create("Song: " + i, "Origin Story: " + i, LocalDate.now().minusYears(25 + i), this.randomize(i), this.artistService.listAll().get((i - 1) % 5).getId());
        }
    }
}

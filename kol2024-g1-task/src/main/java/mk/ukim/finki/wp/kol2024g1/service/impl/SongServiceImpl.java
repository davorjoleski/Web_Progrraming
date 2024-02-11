package mk.ukim.finki.wp.kol2024g1.service.impl;

import mk.ukim.finki.wp.kol2024g1.model.Artist;
import mk.ukim.finki.wp.kol2024g1.model.Genre;
import mk.ukim.finki.wp.kol2024g1.model.Song;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidSongIdException;
import mk.ukim.finki.wp.kol2024g1.repository.SongRepository;
import mk.ukim.finki.wp.kol2024g1.service.SongService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistServiceImpl artistService;


    public SongServiceImpl(SongRepository songRepository, ArtistServiceImpl artistService) {
        this.songRepository = songRepository;
        this.artistService = artistService;
    }

    @Override
    public List<Song> listAll() {
        return songRepository.findAll();
    }

    @Override
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(InvalidSongIdException::new);

    }

    @Override
    public Song create(String name, String originStory, LocalDate dateCreated, Genre genre, Long artist) {
        Artist artist1 = artistService.findById(artist);
        Song song = new Song(name, originStory, dateCreated, genre, artist1);
        songRepository.save(song);
        return song;

    }

    @Override
    public Song update(Long id, String name, String originStory, LocalDate dateCreated, Genre genre, Long artist) {
        Artist artist1 = artistService.findById(artist);
        Song song = findById(id);
        song.setArtist(artist1);
        song.setGenre(genre);
        song.setName(name);
        song.setDateCreated(dateCreated);
        song.setOriginStory(originStory);

        songRepository.save(song);

        return songRepository.save(song);
    }

    @Override
    public Song delete(Long id) {
        Song song = findById(id);
        songRepository.delete(song);
        return song;
    }

    @Override
    public Song vote(Long id) {
        Song song = findById(id);
        song.setVotes(song.getVotes()+1);
        songRepository.save(song);

        return song;


    }

    @Override
    public List<Song> filterSongs(Integer yearsMoreThan, Genre genre) {
          if(yearsMoreThan ==null && genre==null){
              return  songRepository.findAll();
          }
          if(yearsMoreThan==null){
              return songRepository.findSongsByGenre(genre);
          }
          if(genre == null) {
              LocalDate employmentAfter = LocalDate.now().minusYears(yearsMoreThan);

              return songRepository. findByDateCreatedBefore(employmentAfter);
          }
        LocalDate employmentAfter = LocalDate.now().minusYears(yearsMoreThan);

        return songRepository.findByDateCreatedBeforeAndGenre(employmentAfter,genre);
    }
}

package mk.ukim.finki.wp.kol2024g1.service.impl;


import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2024g1.model.Artist;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidArtistIdException;
import mk.ukim.finki.wp.kol2024g1.repository.ArtistRepository;
import mk.ukim.finki.wp.kol2024g1.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElseThrow(InvalidArtistIdException::new);
    }

    @Override
    public List<Artist> listAll() {
        return artistRepository.findAll();
    }

    @Override
    public Artist create(String name) {
        Artist artist = new Artist(name);

        return artistRepository.save(artist);
    }
}

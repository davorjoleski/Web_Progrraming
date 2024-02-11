package mk.ukim.finki.wp.kol2024g1.service;

import mk.ukim.finki.wp.kol2024g1.model.Artist;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidArtistIdException;

import java.util.List;

public interface ArtistService {

    /**
     * @param id The id of the artist that we want to obtain
     * @return The artist with the appropriate id
     * @throws InvalidArtistIdException when there is no artist with the given id
     */
    Artist findById(Long id);

    /**
     * @return List of all artists in the database
     */
    List<Artist> listAll();

    /**
     * This method is used to create a new artist, and save it in the database.
     *
     * @param name
     * @return The artist that is created. The id should be generated when the artist is created.
     */
    Artist create(String name);
}

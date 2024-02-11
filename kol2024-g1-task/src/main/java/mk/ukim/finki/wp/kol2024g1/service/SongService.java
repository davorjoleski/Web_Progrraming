package mk.ukim.finki.wp.kol2024g1.service;

import mk.ukim.finki.wp.kol2024g1.model.Genre;
import mk.ukim.finki.wp.kol2024g1.model.Song;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidArtistIdException;
import mk.ukim.finki.wp.kol2024g1.model.exceptions.InvalidSongIdException;

import java.time.LocalDate;
import java.util.List;

public interface SongService {

    /**
     * @return List of all songs in the database
     */
    List<Song> listAll();

    /**
     * @param id The id of the song that we want to obtain
     * @return The song with the appropriate id
     * @throws InvalidSongIdException when there is no song with the given id
     */
    Song findById(Long id);

    /**
     * This method is used to create a new song, and save it in the database.
     *
     * @param name
     * @param originStory
     * @param dateCreated
     * @param genre
     * @param artist
     * @return The song that is created. The id should be generated when the song is created.
     * @throws InvalidArtistIdException when there is no artist with the given id
     */
    Song create(String name, String originStory, LocalDate dateCreated, Genre genre, Long artist);

    /**
     * This method is used to update a song, and save it in the database.
     *
     * @param id          The id of the song that is being updated
     * @param name
     * @param originStory
     * @param dateCreated
     * @param genre
     * @param artist
     * @return The song that is updated.
     * @throws InvalidSongIdException   when there is no song with the given id
     * @throws InvalidArtistIdException when there is no artist with the given id
     */
    Song update(Long id, String name, String originStory, LocalDate dateCreated, Genre genre, Long artist);

    /**
     * @param id
     * @return The song that is deleted.
     * @throws InvalidSongIdException when there is no song with the given id
     */
    Song delete(Long id);

    /**
     * This method should implement the voting logic, i.e. enable voting for a song.
     *
     * @param id
     * @return The song that is voted for.
     * @throws InvalidSongIdException when there is no song with the given id
     */
    Song vote(Long id);

    /**
     * This method should use repository implementation for the filtering using the appropriate parameters
     *
     * @param yearsMoreThan Used to filter the songs that are older than this value.
     *                      This param can be null, and is not used for filtering in such case.
     * @param genre         Used for filtering the songs by their genre.
     *                      This param can be null, and is not used for filtering in such case.
     * @return The songs that meet the filtering criteria
     */
    List<Song> filterSongs(Integer yearsMoreThan, Genre genre);
}

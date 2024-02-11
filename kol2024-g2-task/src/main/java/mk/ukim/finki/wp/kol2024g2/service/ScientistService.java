package mk.ukim.finki.wp.kol2024g2.service;

import mk.ukim.finki.wp.kol2024g2.model.Scientist;
import mk.ukim.finki.wp.kol2024g2.model.exceptions.InvalidScientistIdException;

import java.util.List;

public interface ScientistService {

    /**
     * returns the scientist with the given id
     *
     * @param id The id of the scientist that we want to obtain
     * @return The scientist with the appropriate id
     * @throws InvalidScientistIdException when there is no scientist with the given id
     */
    Scientist findById(Long id);

    /**
     * @return List of all scientists in the database
     */
    List<Scientist> listAll();

    /**
     * This method is used to create a new scientist, and save it in the database.
     *
     * @param name
     * @return The scientist that is created. The id should be generated when the scientist is created.
     */
    Scientist create(String name);
}

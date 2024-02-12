package mk.ukim.finki.wp.kol2024g3.service;

import mk.ukim.finki.wp.kol2024g3.model.Presentation;
import mk.ukim.finki.wp.kol2024g3.model.PresentationType;
import mk.ukim.finki.wp.kol2024g3.model.exceptions.InvalidPresentationIdException;
import mk.ukim.finki.wp.kol2024g3.model.exceptions.InvalidScientistIdException;

import java.time.LocalDate;
import java.util.List;

public interface PresentationService {

    /**
     * @return List of all presentations in the database
     */
    List<Presentation> listAll();

    /**
     * @param id The id of the presentation that we want to obtain
     * @return The presentation with the appropriate id
     * @throws InvalidPresentationIdException when there is no presentation with the given id
     */
    Presentation findById(Long id);

    /**
     * This method is used to create a new presentation, and save it in the database.
     *
     * @param name
     * @param description
     * @param datePresented
     * @param presentationType
     * @param scientist
     * @return The presentation that is created. The id should be generated when the presentation is created.
     * @throws InvalidScientistIdException when there is no scientist with the given id
     */
    Presentation create(String name, String description, LocalDate datePresented, PresentationType presentationType, Long scientist);

    /**
     * This method is used to update a presentation, and save it in the database.
     *
     * @param id          The id of the presentation that is being updated
     * @param name
     * @param description
     * @param datePresented
     * @param presentationType
     * @param scientist
     * @return The presentation that is updated.
     * @throws InvalidPresentationIdException   when there is no presentation with the given id
     * @throws InvalidScientistIdException when there is no scientist with the given id
     */
    Presentation update(Long id, String name, String description, LocalDate datePresented, PresentationType presentationType, Long scientist);

    /**
     * @param id
     * @return The presentation that is deleted.
     * @throws InvalidPresentationIdException when there is no presentation with the given id
     */
    Presentation delete(Long id);

    /**
     * This method should implement the voting logic, i.e. enable voting for a presentation.
     *
     * @param id
     * @return The presentation that is voted for.
     * @throws InvalidPresentationIdException when there is no presentation with the given id
     */
    Presentation vote(Long id);

    /**
     * This method should use repository implementation for the filtering using the appropriate parameters
     *
     * @param yearsMoreThan Used to filter the presentations that are presented before more years than the value of this parameter.
     *                      This param can be null, and is not used for filtering in such case.
     * @param presentationType         Used for filtering the presentations by their presentationType.
     *                                This param can be null, and is not used for filtering in such case.
     * @return The presentations that meet the filtering criteria
     */
    List<Presentation> filterPresentations(Integer yearsMoreThan, PresentationType presentationType);
}

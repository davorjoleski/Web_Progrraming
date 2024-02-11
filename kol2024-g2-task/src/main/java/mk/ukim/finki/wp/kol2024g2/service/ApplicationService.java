package mk.ukim.finki.wp.kol2024g2.service;

import mk.ukim.finki.wp.kol2024g2.model.Application;
import mk.ukim.finki.wp.kol2024g2.model.ApplicationType;
import mk.ukim.finki.wp.kol2024g2.model.exceptions.InvalidScientistIdException;
import mk.ukim.finki.wp.kol2024g2.model.exceptions.InvalidApplicationIdException;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationService {

    /**
     * @return List of all applications in the database
     */
    List<Application> listAll();

    /**
     * @param id The id of the application that we want to obtain
     * @return The application with the appropriate id
     * @throws InvalidApplicationIdException when there is no application with the given id
     */
    Application findById(Long id);

    /**
     * This method is used to create a new application, and save it in the database.
     *
     * @param name
     * @param originStory
     * @param dateCreated
     * @param applicationType
     * @param scientist
     * @return The application that is created. The id should be generated when the application is created.
     * @throws InvalidScientistIdException when there is no scientist with the given id
     */
    Application create(String name, String originStory, LocalDate dateCreated, ApplicationType applicationType, Long scientist);

    /**
     * This method is used to update an application, and save it in the database.
     *
     * @param id          The id of the application that is being updated
     * @param name
     * @param originStory
     * @param dateCreated
     * @param applicationType
     * @param scientist
     * @return The application that is updated.
     * @throws InvalidApplicationIdException   when there is no application with the given id
     * @throws InvalidScientistIdException when there is no scientist with the given id
     */
    Application update(Long id, String name, String originStory, LocalDate dateCreated, ApplicationType applicationType, Long scientist);

    /**
     * @param id
     * @return The application that is deleted.
     * @throws InvalidApplicationIdException when there is no application with the given id
     */
    Application delete(Long id);

    /**
     * This method should implement the voting logic, i.e. enable voting for an application.
     *
     * @param id
     * @return The application that is voted for.
     * @throws InvalidApplicationIdException when there is no application with the given id
     */
    Application vote(Long id);

    /**
     * This method should use repository implementation for the filtering using the appropriate parameters
     *
     * @param yearsMoreThan Used to filter the applications that are older than this value.
     *                      This param can be null, and is not used for filtering in such case.
     * @param applicationType         Used for filtering the applications by their applicationType.
     *                                This param can be null, and is not used for filtering in such case.
     * @return The applications that meet the filtering criteria
     */
    List<Application> filterApplications(Integer yearsMoreThan, ApplicationType applicationType);
}

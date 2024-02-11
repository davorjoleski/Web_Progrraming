package mk.ukim.finki.wp.kol2024g2.web;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.kol2024g2.model.Application;
import mk.ukim.finki.wp.kol2024g2.model.ApplicationType;
import mk.ukim.finki.wp.kol2024g2.model.Scientist;
import mk.ukim.finki.wp.kol2024g2.service.ApplicationService;
import mk.ukim.finki.wp.kol2024g2.service.ScientistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


@Controller
public class ApplicationsController {

    private ScientistService scientistService;
    private ApplicationService applicationService;

    public ApplicationsController(ScientistService scientistService, ApplicationService applicationService) {
        this.scientistService = scientistService;
        this.applicationService = applicationService;
    }


    /**
     * This method should use the "list.html" template to display all applications.
     * The method should be mapped on paths '/' and '/applications'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all applications should be displayed.
     * If one, or both of the arguments are not 'null', the applications that are the result of the call
     * to the method 'filterApplications' from the ApplicationService should be displayed.
     *
     * @param years
     * @param applicationType
     * @return The view "list.html".
     */
    @GetMapping({"/applications", "/"})
    public String listAll(@RequestParam(required = false) Integer years, @RequestParam(required = false) ApplicationType applicationType, Model model) {

        List<Scientist> scientistList = scientistService.listAll();
        List<Application> applications;

        if (years == null && applicationType == null) {
            applications = applicationService.listAll();
        } else {
            applications = this.applicationService.filterApplications(years, applicationType);

        }
        model.addAttribute("apptype", ApplicationType.values());

        model.addAttribute("scientists", scientistList);
        model.addAttribute("applications", applications);


        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/applications/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/applications/add")
    public String showAdd(Model model) {

        model.addAttribute("apptype", ApplicationType.values());

        model.addAttribute("scientists", this.scientistService.listAll());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the application that is updated.
     * The method should be mapped on path '/applications/edit/[id]'.
     *
     * @return The view "form.html".
     */

    @GetMapping("/applications/edit/{id}")

    public String showEdit(@PathVariable Long id,Model model) {
        model.addAttribute("api",this.applicationService.findById(id));


        model.addAttribute("apptype", ApplicationType.values());

        model.addAttribute("scientists", this.scientistService.listAll());
        return "form";
    }

    /**
     * This method should create a new application given the arguments it takes.
     * The method should be mapped on path '/applications'.
     * After the application is created, all applications should be displayed.
     *
     * @return The view "list.html".
     */


    @PostMapping("/applications")

    public String create(@RequestParam String name,
                         @RequestParam String originStory,
                         @RequestParam LocalDate dateCreated,
                         @RequestParam ApplicationType applicationType,
                         @RequestParam Long scientist) {
        this.applicationService.create(name, originStory, dateCreated, applicationType, scientist);

        return "redirect:/applications";
    }

    /**
     * This method should update an application given the arguments it takes.
     * The method should be mapped on path '/applications/[id]'.
     * After the application is updated, all applications should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/applications/{id}")
    public String update(@PathVariable Long id,
                        @RequestParam String name,
                        @RequestParam String originStory,
                        @RequestParam LocalDate dateCreated,
                        @RequestParam ApplicationType applicationType,
                        @RequestParam Long scientist) {
        this.applicationService.update(id,name,originStory,dateCreated,applicationType,scientist);
        return "redirect:/applications";
    }

    /**
     * This method should delete the application that has the appropriate identifier.
     * The method should be mapped on path '/applications/delete/[id]'.
     * After the application is deleted, all applications should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/applications/delete/{id}")
    public String delete(@PathVariable Long id) {
        applicationService.delete(id);
        return "redirect:/applications";
    }

    /**
     * This method should increase the number of votes of the appropriate application by 1.
     * The method should be mapped on path '/applications/vote/[id]'.
     * After the operation, all applications should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/applications/vote/{id}")
    public String vote(@PathVariable Long id) {

        applicationService.vote(id);

        return "redirect:/applications";
    }
}

package mk.ukim.finki.wp.kol2024g3.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.kol2024g3.model.Presentation;
import mk.ukim.finki.wp.kol2024g3.model.PresentationType;
import mk.ukim.finki.wp.kol2024g3.service.PresentationService;
import mk.ukim.finki.wp.kol2024g3.service.ScientistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class PresentationsController {

    private final PresentationService presentationService;
    private final ScientistService scientistService;

    /**
     * This method should use the "list.html" template to display all presentations.
     * The method should be mapped on paths '/' and '/presentations'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all presentations should be displayed.
     * If one, or both of the arguments are not 'null', the presentations that are the result of the call
     * to the method 'filterPresentations' from the PresentationService should be displayed.
     *
     * @param years
     * @param presentationType
     * @return The view "list.html".
     */
    @GetMapping({"/", "/presentations"})
    public String listAll(@RequestParam (required = false) Integer years, @RequestParam (required = false) PresentationType presentationType, Model model) {
        List<Presentation> presentations;



        if (years == null && presentationType == null) {
            presentations = presentationService.listAll();
        }
        else {
            presentations = presentationService.filterPresentations(years, presentationType);
        }

        model.addAttribute("prestype",PresentationType.values());
        model.addAttribute("present",presentations);

        return "list";

    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/presentations/add'.
     *
     * @return The view "form.html".
     */

    @GetMapping("/presentations/add")
    public String showAdd(Model model) {

        model.addAttribute("presty",PresentationType.values());
        model.addAttribute("scientist",scientistService.listAll());

        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the presentation that is updated.
     * The method should be mapped on path '/presentations/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/presentations/edit/{id}")
    public String showEdit(@PathVariable Long id,Model model) {
        Presentation pres =presentationService.findById(id);

        model.addAttribute("presen",pres);
        model.addAttribute("presty",PresentationType.values());
        model.addAttribute("scientist",scientistService.listAll());

        return "form";
    }

    /**
     * This method should create a new presentation given the arguments it takes.
     * The method should be mapped on path '/presentations'.
     * After the presentation is created, all presentations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/presentations")
    public String create(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam LocalDate datePresented,
                         @RequestParam PresentationType presentationType,
                         @RequestParam Long scientist) {
        this.presentationService.create(name,description,datePresented,presentationType,scientist);
        return "redirect:/presentations";
    }

    /**
     * This method should update a presentation given the arguments it takes.
     * The method should be mapped on path '/presentations/[id]'.
     * After the presentation is updated, all presentations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/presentations/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam LocalDate datePresented,
                         @RequestParam PresentationType presentationType,
                         @RequestParam Long scientist) {

        this.presentationService.update(id,name,description,datePresented,presentationType,scientist);

        return "redirect:/presentations";
    }

    /**
     * This method should delete the presentation that has the appropriate identifier.
     * The method should be mapped on path '/presentations/delete/[id]'.
     * After the presentation is deleted, all presentations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/presentations/delete/{id}")
    public String delete(@PathVariable Long id) {
        presentationService.delete(id);

        return "redirect:/presentations";
    }

    /**
     * This method should increase the number of votes of the appropriate presentation by 1.
     * The method should be mapped on path '/presentations/vote/[id]'.
     * After the operation, all presentations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/presentations/vote/{id}")
    public String vote(@PathVariable Long id) {
          presentationService.vote(id);


        return "redirect:/presentations";
    }
}

package mk.ukim.finki.wp.kol2024g1.web;

import mk.ukim.finki.wp.kol2024g1.model.Artist;
import mk.ukim.finki.wp.kol2024g1.model.Genre;
import mk.ukim.finki.wp.kol2024g1.model.Song;
import mk.ukim.finki.wp.kol2024g1.service.ArtistService;
import mk.ukim.finki.wp.kol2024g1.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class SongsController {

    private final SongService songService;
    private final ArtistService artistService;

    public SongsController(SongService songService, ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;
    }

    /**
     * This method should use the "list.html" template to display all songs.
     * The method should be mapped on paths '/' and '/songs'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all songs should be displayed.
     * If one, or both of the arguments are not 'null', the songs that are the result of the call
     * to the method 'filterSongs' from the SongService should be displayed.
     *
     * @param years
     * @param genre
     * @return The view "list.html".
     */
    @GetMapping({"/songs", "/"})

    public String listAll(@RequestParam(required = false) Integer years, @RequestParam(required = false) Genre genre, Model model) {

        List<Artist> artists=artistService.listAll();
        List<Song> songs ;

        if(years== null && genre==null){
            songs = this.songService.listAll();
        }
        else {
            songs= this.songService.filterSongs(years,genre);
        }
        model.addAttribute("songs",songs);
        model.addAttribute("artists",artists);
        model.addAttribute("genre",Genre.values());


        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/songs/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/songs/add")
    public String showAdd(Model model) {
        model.addAttribute("genre",Genre.values());
        model.addAttribute("artists",artistService.listAll());

           return     "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the song that is updated.
     * The method should be mapped on path '/songs/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/songs/edit/{id}")
    public String showEdit(@PathVariable Long id,Model model) {
        Song song = this.songService.findById(id);
        List<Artist> artists = this.artistService.listAll();
        model.addAttribute("artists",artists);
        model.addAttribute("genre",Genre.values());
        model.addAttribute("song",song);


        return "form";
    }

    /**
     * This method should create a song given the arguments it takes.
     * The method should be mapped on path '/songs'.
     * After the song is created, all songs should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/songs")
    public String create(@RequestParam String name,
                         @RequestParam String originStory,
                         @RequestParam LocalDate dateCreated,
                         @RequestParam Genre genre,
                         @RequestParam Long artist) {
        this.songService.create(name, originStory, dateCreated, genre, artist);
        return "redirect:/songs";
    }


    /**
     * This method should update a song given the arguments it takes.
     * The method should be mapped on path '/songs/[id]'.
     * After the song is updated, all songs should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/songs/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String originStory,
                         @RequestParam LocalDate dateCreated,
                         @RequestParam Genre genre,
                         @RequestParam Long artist) {
        this.songService.update(id,name,originStory,dateCreated,genre,artist);
        return "redirect:/songs";
    }

    /**
     * This method should delete the song that has the appropriate identifier.
     * The method should be mapped on path '/songs/delete/[id]'.
     * After the song is deleted, all songs should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/songs/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.songService.delete(id);
        return "redirect:/songs";
    }

    /**
     * This method should increase the number of votes of the appropriate song by 1.
     * The method should be mapped on path '/songs/vote/[id]'.
     * After the operation, all songs should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("songs/vote/{id}")
    public String vote(@PathVariable Long id) {
        this.songService.vote(id);
        return "redirect:/songs";
    }
}

package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.*;
import mk.ukim.finki.ecinema.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final CategoryService categoryService;
    private final DirectorService directorService;
    private final TicketService ticketService;
    private final UserService userService;

    public MovieController(MovieService movieService, CategoryService categoryService, DirectorService directorService, TicketService ticketService, UserService userService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.directorService = directorService;
        this.ticketService = ticketService;
        this.userService = userService;
    }
    @GetMapping("/movies")
    public String showMovies(Model model,
                             @RequestParam(required = false) String error,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Long categoryId,
                             HttpServletRequest req){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        req.getSession().setAttribute("subscription",this.userService.findByUsername(username).getSubscription());

        List<Movie> movies;
        List<Category> categories = this.categoryService.listAll();
        List<Director> directors = this.directorService.findAll();
        if (name != null || categoryId != null){
            movies = this.movieService.filter(name, categoryId);
        }
        else {
            movies= this.movieService.findAll();
        }
        model.addAttribute("movies",movies);
        model.addAttribute("categories", categories);
        model.addAttribute("directors", directors);

        model.addAttribute("bodyContent","movies");

        return "master-template";
    }
    @GetMapping("/movies/add-form")
    public String showAddMovie(Model model){
        List<Category> categories = this.categoryService.listAll();
        List<Director> directors = this.directorService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("directors", directors);

        model.addAttribute("bodyContent","add-movie");
        return "master-template";
    }

    @GetMapping("/movies/{id}/edit")
    public String showEditMovie(@PathVariable Long id, Model model){
        if (this.movieService.findById(id) != null) {
            Movie movie = this.movieService.findById(id);
            List<Category> categories = this.categoryService.listAll();
            List<Director> directors = this.directorService.findAll();

            model.addAttribute("categories", categories);
            model.addAttribute("movie", movie);
            model.addAttribute("directors", directors);

            model.addAttribute("bodyContent","add-movie");
            return "master-template";
        }
        return "redirect:/movies?error=MovieNotFound";
    }

    @PostMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id){
        this.movieService.deleteById(id);
        return "redirect:/movies";
    }
    @PostMapping("/movies/add")
    public String saveMovie(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Category category,
            @RequestParam Director director,
            @RequestParam String premiereStart,
            @RequestParam String premiereEnd,
            @RequestParam Double ticketPrice,
            @RequestParam String url) {
        if (id != null) {
            this.movieService.update(id,name,description,director,category,LocalDateTime.parse(premiereStart),LocalDateTime.parse(premiereEnd), this.ticketService.findAll(), url);
        } else {
            List<Ticket> tickets = new ArrayList<>();
            tickets.add(new Ticket(ticketPrice, ticketPrice));
            this.movieService.create(name,description, director, category, LocalDateTime.parse(premiereStart),LocalDateTime.parse(premiereEnd), tickets, url);
        }
        return "redirect:/movies";
    }
    @GetMapping("/movies/trailer")
    public String getTrailer(Model model){
        model.addAttribute("bodyContent", "trailer");
        return "master-template";
    }
}

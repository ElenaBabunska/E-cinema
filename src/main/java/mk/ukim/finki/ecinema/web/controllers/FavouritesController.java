package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.Favourite;
import mk.ukim.finki.ecinema.model.Movie;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.service.FavouriteService;
import mk.ukim.finki.ecinema.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FavouritesController {

    private final FavouriteService favouriteService;
    private final UserService userService;

    public FavouritesController(FavouriteService favouriteService, UserService userService) {
        this.favouriteService = favouriteService;
        this.userService = userService;
    }
    @GetMapping("/favourites")
    public String getFavouritesPage(@RequestParam(required = false) String error,
                                   HttpServletRequest req,
                                   Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = req.getRemoteUser();
        Favourite favourite = this.favouriteService.getActiveFavourite(username);
        req.getSession().setAttribute("fave",favourite.getId());

        List<Movie> movies = this.favouriteService.listAllFavouriteMovie(favourite.getId());
        model.addAttribute("favourites", movies);

        model.addAttribute("bodyContent","favourites");
        return "master-template";
    }

    @PostMapping("/favourites/{id}")
    public String addMovieToFavourites(
            @PathVariable Long id, HttpServletRequest request) {
        try {
            String username = request.getRemoteUser();
            User user = this.userService.findByUsername(username);
            this.favouriteService.addMovieToFavourite(user.getUsername(), id);
            return "redirect:/favourites";
        } catch (RuntimeException exception) {
            return "redirect:/favourites?error=" + exception.getMessage();
        }
    }

    @PostMapping("/favourites/{id}/delete")
    public String deleteMovieFromFavourite(@PathVariable Long id, HttpServletRequest request) {
        String username = request.getRemoteUser();
        favouriteService.delete(id, username);
        return "redirect:/favourites";
    }
}

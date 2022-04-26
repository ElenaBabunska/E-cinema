package mk.ukim.finki.ecinema.web.controllers;


import mk.ukim.finki.ecinema.model.ShoppingCart;
import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/shopping-cart")
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("tickets", this.shoppingCartService.listAllTicketsInShoppingCart(shoppingCart.getId()));

        model.addAttribute("bodyContent","shopping-cart");
        return "master-template";

    }

    @GetMapping ("/shopping-cart/add/{id}")
    public String addTicketToShoppingCart(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addTicketToShoppingCart(user.getUsername(), id);
            return "redirect:/tickets";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }

    @PostMapping("/shopping-cart/delete/{id}")
    public String deleteTicketFromShoppingCart(@PathVariable Long id, Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.deleteTicketFromShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception) {
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }
}


package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.*;
import mk.ukim.finki.ecinema.service.DiscountService;
import mk.ukim.finki.ecinema.service.MovieService;
import mk.ukim.finki.ecinema.service.TicketService;
import mk.ukim.finki.ecinema.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;


@Controller
public class TicketsController {
    private final TicketService ticketService;
    private final MovieService movieService;
    private final UserService userService;
    private final DiscountService discountService;

    public TicketsController(TicketService ticketService, MovieService movieService, UserService userService, DiscountService discountService) {
        this.ticketService = ticketService;
        this.movieService = movieService;
        this.userService = userService;
        this.discountService = discountService;
    }

    @GetMapping("/tickets")
    public String showTickets(@RequestParam(required = false) String error,
                              Model model,
                              HttpServletRequest req){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        req.getSession().setAttribute("subscription",this.userService.findByUsername(username).getSubscription());
        List<Ticket> tickets = this.ticketService.findAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("bodyContent","tickets");
        return "master-template";
    }
    @GetMapping("/tickets/{id}/edit")
    public String showEditTicket(@PathVariable Long id, Model model){
        if (this.ticketService.findById(id).isPresent()) {
            Ticket ticket = this.ticketService.findById(id).get();
            model.addAttribute("ticket", ticket);

            model.addAttribute("bodyContent","add-ticket");
            return "master-template";

        }
        return "redirect:/tickets?error=TicketNotFound";
    }

    @GetMapping("/tickets/{id}/delete")
    public String deleteTicket(@PathVariable Long id){
        this.ticketService.deleteById(id);
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/edit")
    public String saveTicket(
            @RequestParam Long id,
            @RequestParam Double price) {
        this.ticketService.update(id, price);
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/use-discount/{id}")
    public String calculateDiscount(@PathVariable Long id, Model model){
        List<Ticket> tickets = this.ticketService.findAll();
        Discount discount = this.discountService.findById(id);
        Double value = discount.getValue();

        if (LocalDate.now().isAfter(discount.getValidUntil())){
            return "discount-error";
        }
        else {
            for (Ticket ticket : tickets) {
                ticket.setDiscountPrice(ticket.getPrice() - (ticket.getPrice() * (value / 100.0)));
                this.ticketService.update2(ticket.getCode(),ticket.getPrice() - (ticket.getPrice() * (value / 100.0)));
            }

            model.addAttribute("tickets", tickets);
            model.addAttribute("bodyContent","tickets");
            return "master-template";
        }
    }

}




package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.*;
import mk.ukim.finki.ecinema.service.DiscountService;
import mk.ukim.finki.ecinema.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


@Controller
public class DiscountController {

    private final DiscountService discountService;
    private final TicketService ticketService;

    public DiscountController(DiscountService discountService, TicketService ticketService) {
        this.discountService = discountService;
        this.ticketService = ticketService;
    }

    @GetMapping("/discounts")
    public String showDiscounts(Model model){
        List<Discount> discounts = this.discountService.findAll();
        model.addAttribute("discounts", discounts);

        model.addAttribute("bodyContent","discounts");
        return "master-template";
    }

    //samo da se pokazhe forma za dodavanje popust, a posle kje se prefrli na create metodot
    @GetMapping("/discounts/add")
    public String showAddDiscount(Model model){
        model.addAttribute("bodyContent","add-discount");
        return "master-template";
    }

    @GetMapping("/discounts/{id}/edit")
    public String showEditDiscount(@PathVariable Long id, Model model){
        Discount discount = this.discountService.findById(id);
        model.addAttribute("discount", discount);
        model.addAttribute("bodyContent","add-discount");
        return "master-template";
    }

    @PostMapping("/discounts/create")
    public String updateDiscount(@RequestParam(required = false) Long id,
                                 @RequestParam String validUntil,
                                 @RequestParam String name,
                                 @RequestParam Double value){
        if (id!=null){
            this.discountService.update(id, LocalDate.parse(validUntil), name, value);
        }else {
            this.discountService.create(LocalDate.parse(validUntil), name, value);
        }
        return "redirect:/discounts";
    }

    @PostMapping("/discounts/{id}/delete")
    public String deleteDiscount(@PathVariable Long id){
        this.discountService.deleteById(id);
        return "redirect:/discounts";
    }

}

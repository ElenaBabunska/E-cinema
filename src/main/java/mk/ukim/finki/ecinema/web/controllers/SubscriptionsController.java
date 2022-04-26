package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.Subscription;
import mk.ukim.finki.ecinema.model.exceptions.CouldNotDeleteSubscriptionException;
import mk.ukim.finki.ecinema.service.SubscriptionService;
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
public class SubscriptionsController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    public SubscriptionsController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @GetMapping("/subscriptions")
    public String getSubscriptionPage (@RequestParam(required = false) String error,
                                   HttpServletRequest req,
                                   Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("subscriptions", this.subscriptionService.findAll());

        model.addAttribute("bodyContent","subscriptions");
        return "master-template";

    }


    @PostMapping("/subscriptions/delete/{id}")
    public String deleteSubscriptions(@PathVariable Long id){
        this.subscriptionService.deleteById(id);
        return "redirect:/subscriptions";
    }

    @GetMapping("/subscriptions/add-form")
    public String addSubscriptionsPage(Model model) {
        List<Subscription> subscriptions = this.subscriptionService.findAll();
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("bodyContent","add-subscriptions");
        return "master-template";
    }

    @GetMapping("/subscriptions/edit-form/{id}")
    public String editSubscriptionsPage(@PathVariable Long id, Model model) {
        if (this.subscriptionService.findById(id) != null) {
            Subscription subscription = this.subscriptionService.findById(id);
            model.addAttribute("subscription", subscription);

            model.addAttribute("bodyContent","add-subscriptions");
            return "master-template";
        }
        return "redirect:/subscriptions?error=SubscriptionNotFound";
    }

    @PostMapping("/subscriptions/add")
    public String saveSubscription(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String url
            ) {
        if (id != null) {
            this.subscriptionService.update(id,name,description,price);
        } else {
            this.subscriptionService.save(name,description,price,url);
        }
        return "redirect:/subscriptions";
    }

    @GetMapping("/subscriptions/apply/{id}")
    public String applyToSubscription(HttpServletRequest request,
                                 @PathVariable Long id) {
        String username = request.getRemoteUser();
        this.userService.addUserToSubscription(id, username);
        return "redirect:/home";
    }

    @GetMapping("/subscriptions/users/{id}")
    public String usersInSubscription(Model model,
                                 @PathVariable Long id) {
        Subscription subscription = this.subscriptionService.findById(id);
        model.addAttribute("subscription",subscription);
        model.addAttribute("users",this.subscriptionService.listUsersInProgram(id));
        return "users-in-subs";
    }
}

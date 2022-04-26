package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.enumerations.Role;
import mk.ukim.finki.ecinema.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.ecinema.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.ecinema.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.ecinema.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }


    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam Role role){
        try{
            this.userService.register(username, password, repeatedPassword, name, surname, role);
            return "redirect:/login"; //otkako ke se registrira neka se pretplati pa posle login
        }
        catch (PasswordsDoNotMatchException | InvalidArgumentsException | UsernameAlreadyExistsException exception) {
            return "redirect:/register?error="+ exception.getMessage();
        }
    }
}


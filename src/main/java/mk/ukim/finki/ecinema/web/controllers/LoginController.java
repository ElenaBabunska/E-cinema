package mk.ukim.finki.ecinema.web.controllers;

import mk.ukim.finki.ecinema.model.User;
import mk.ukim.finki.ecinema.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.ecinema.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.ecinema.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model){
        User user = null;
        try{
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user",user);
            return "redirect:/home";
        }catch(InvalidUserCredentialsException | InvalidArgumentsException ex){
            model.addAttribute("hasError",true);
            model.addAttribute("error", ex.getMessage());

            model.addAttribute("bodyContent","login");
            return "master-template";
        }
    }
}

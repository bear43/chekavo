package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.impl.UserServiceImpl;

@Controller
public class AuthController
{
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public String login_get()
    {
        return "login";
    }

    @GetMapping("/registration")
    public String registration_get()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration_post(Model model, User user, RedirectAttributes attr)
    {
        user.setActive(true);
        try
        {
            userService.createUser(user);
        }
        catch (Exception ex)
        {
            model.addAttribute("message", String.format("User is not registered: %s", ex.getMessage()));
            return "registration";
        }
        attr.addFlashAttribute("message", "User is registered successful");
        return "redirect:/login";
    }
}

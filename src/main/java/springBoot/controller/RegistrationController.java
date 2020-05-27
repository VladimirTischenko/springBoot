package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springBoot.domain.User;
import springBoot.service.UserService;

import java.util.Map;

/**
 * Created by Vladimir on 22.05.2020.
 */
@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        boolean b = userService.add(user);

        if (!b) {
            model.put("message", "User already exist!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/mailConfirmation/{code}")
    public String mailConfirmation(@PathVariable String code, Model model) {
        boolean isActivate = userService.activate(code);

        if (isActivate) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code not found");
        }

        return "login";
    }
}

package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springBoot.domain.User;
import springBoot.repository.UserRepository;

import java.util.Map;

/**
 * Created by Vladimir on 22.05.2020.
 */
@Controller
@RequestMapping(RegistrationController.REST_URL)
public class RegistrationController {
    static final String REST_URL = "/registration";
    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String registration() {
        return "registration";
    }

    @PostMapping()
    public String addNewUser (User user, Map<String, Object> model) {
        User userByName = userRepository.findByName(user.getName());

        if(userByName != null) {
            model.put("message", "User already exist!");
            return "registration";
        }

        userRepository.save(user);
        return "redirect:/login";
    }

}

package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springBoot.domain.User;
import springBoot.service.UserService;

/**
 * Created by Vladimir on 28.05.2020.
 */
@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    static final String REST_URL = "/profile";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User update(@AuthenticationPrincipal User user, @RequestBody User updatedUser) {
        return userService.update(user, updatedUser);
    }
}

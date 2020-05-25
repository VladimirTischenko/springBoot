package springBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Vladimir on 22.05.2020.
 */
@Controller
public class RootController {
    @GetMapping()
    public String root() {
        return "loginSuccess";
    }
}

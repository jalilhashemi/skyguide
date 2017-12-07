package ch.fhnw.skyguide.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
@CrossOrigin
public class IndexController {
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "index";
    }
}

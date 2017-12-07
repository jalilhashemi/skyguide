package ch.fhnw.skyguide.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
@CrossOrigin
public class IndexController {
    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "index";
    }
}

package ch.fhnw.skyguide.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String sayHello( HttpServletResponse response,
                           HttpServletRequest request) throws IOException {
        return "<h3>Hello skyguide";
    }

}

package ch.fhnw.skyguide.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class FrontController {
    @RequestMapping(method = RequestMethod.GET)
    public String showFrontend( HttpServletResponse response,
                            HttpServletRequest request) throws IOException {
        return "index";
    }
}

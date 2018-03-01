package ch.fhnw.skyguide.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/language")
public class MutliLanguageController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(HttpServletResponse response,
                           HttpServletRequest request) throws IOException {
        return "<h3>" + getMessage("hello", "fr");
    }

    public String getMessage(String code, String locale) {
        return messageSource.getMessage(code, null, new Locale(locale));
    }

}

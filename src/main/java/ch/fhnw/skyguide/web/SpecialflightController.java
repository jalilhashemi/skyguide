package ch.fhnw.skyguide.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialflights")
public class SpecialflightController {
    private static final Log logger = LogFactory.getLog(SpecialflightController.class);

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Specialflight findById(@PathVariable String id, Model model) {
//        return new Specialflight(Integer.parseInt(id), "content");
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String findById() {
        return "redirect:/index";
    }


}

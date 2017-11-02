package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.Specialflight;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialflights")
public class SpecialflightCpntroller {
    private static final Log logger = LogFactory.getLog(SpecialflightCpntroller.class);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Specialflight findById(@PathVariable String id, Model model) {
        return new Specialflight(Integer.parseInt(id), "content");
    }

}

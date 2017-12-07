package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.service.ActivityType;
import ch.fhnw.skyguide.service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/information")
@CrossOrigin
public class InformationController {

    @Autowired
    private ActivityTypeService activityTypeService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ActivityType> getInformations(HttpServletResponse response,
                                              HttpServletRequest request) throws IOException {
        List<ActivityType> list = activityTypeService.getAllActivityTypes();
        return list;
    }
}

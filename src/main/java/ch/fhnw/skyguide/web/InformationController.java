package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.ActivityType;
import ch.fhnw.skyguide.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    private ActivityTypeService aircraftTypeService;

    //@CrossOrigin(origins = "http://localhost")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ActivityType> getInformations(HttpServletResponse response,
                                              HttpServletRequest request) throws IOException {
   /*     List<ActivityType> list = new ArrayList<>();
        ActivityType a = new ActivityType("Sky Latern");
        Field f = new Field();
        f.setActive(true);
        f.setMandatory(true);
        f.setName("date");
        a.getFieldList().add(f);
        list.add(a);*/
   List<ActivityType> list = aircraftTypeService.getAllAircraftTypes();
        return list;
            /*    "information = {" +
                "'name': 'Sky Latern'" +
                "'fields': {" +
                "'date': 'true'," +
                "'duration': 'true'" +
                "}" +
                "}";*/
    }
}

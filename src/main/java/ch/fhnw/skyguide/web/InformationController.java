package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.AircraftType;
import ch.fhnw.skyguide.AircraftTypeService;
import ch.fhnw.skyguide.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    private AircraftTypeService aircraftTypeService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<AircraftType> getInformations(HttpServletResponse response,
                                              HttpServletRequest request) throws IOException {
   /*     List<AircraftType> list = new ArrayList<>();
        AircraftType a = new AircraftType("Sky Latern");
        Field f = new Field();
        f.setActive(true);
        f.setMandatory(true);
        f.setName("date");
        a.getFieldList().add(f);
        list.add(a);*/
   List<AircraftType> list = aircraftTypeService.getAllAircraftTypes();
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

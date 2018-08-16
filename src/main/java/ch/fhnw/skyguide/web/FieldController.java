package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.*;
import ch.fhnw.skyguide.persistence.FormTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/fields")
public class FieldController {

    @Autowired
    FormTypeRepository formTypeRepository;

    @RequestMapping(method = RequestMethod.GET, params = {"activityType", "aircraftType"})
    public ResponseEntity<Iterable<Field>> getByType(@RequestParam("activityType") String activityType, @RequestParam("aircraftType") String aircraftType) {
        Set<Field> fields = formTypeRepository.findByActivityType_NameAndAircraftType_Name(activityType, aircraftType).getFields();
        return new ResponseEntity<>(fields, HttpStatus.CREATED);
    }
}

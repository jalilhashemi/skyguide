package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.*;
import ch.fhnw.skyguide.persistence.*;
import ch.fhnw.skyguide.util.EmailSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    AircraftTypeRepository aircraftTypeRepository;

    @Autowired
    HeightTypeRepository heightTypeRepository;

    @Autowired
    CoordinateRepository coordinateRepository;

    @Autowired
    EmailSender emailSender;

    @Autowired
    private ModelMapper modelMapper;


    // Not supported in production
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Application>> findAll() {
        Iterable<Application> applications = applicationRepository.findAll();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApplicationDTO> create(@RequestBody ApplicationDTO applicationDTO) {
        Application application = convertToEntity(applicationDTO);
        application.setAdminKey(UUID.randomUUID().toString());
        application.setViewKey(UUID.randomUUID().toString());
        if (application != null) {
            application = applicationRepository.save(application);
            emailSender.send(application.getEmail(), application.getAdminKey(), application.getViewKey());
            return new ResponseEntity<>(convertToDto(application), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Application> findById(@PathVariable("id") String id) {
        Application application = applicationRepository.findByAdminKey(id);
        if(application != null)
            return new ResponseEntity<>(application, HttpStatus.OK);

        application = applicationRepository.findByKey(id);
        if(application != null)
            return new ResponseEntity<>(application, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ApplicationDTO convertToDto(Application application) {
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        applicationDTO.setActivityType(application.getActivityType().getName());
        applicationDTO.setAircraftType(application.getAircraftType().getName());
        applicationDTO.setHeightType(application.getHeightType().getName());

        List<CoordinateDTO> coordinatesDTO = new ArrayList<>();
        for (Coordinate c : application.getCoordinates())
            coordinatesDTO.add(convertToDto(c));

        applicationDTO.setCoordinates(coordinatesDTO);


        return applicationDTO;
    }

    private Application convertToEntity(ApplicationDTO applicationDTO) {
        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setActivityType(activityTypeRepository.findByName(applicationDTO.getActivityType()));
        application.setAircraftType(aircraftTypeRepository.findByName(applicationDTO.getAircraftType()));
        application.setHeightType(heightTypeRepository.findByName(applicationDTO.getHeightType()));

        Set<Coordinate> coordinates = new HashSet<>();
        for(CoordinateDTO c : applicationDTO.getCoordinates()) {
            Coordinate coordinate = coordinateRepository.save(convertToEntity(c));
            coordinates.add(coordinate);
        }

        application.setCoordinates(coordinates);

        return application;
    }

    private CoordinateDTO convertToDto(Coordinate coordinate) {
        return modelMapper.map(coordinate, CoordinateDTO.class);
    }

    private Coordinate convertToEntity(CoordinateDTO coordinateDTO) {
        return modelMapper.map(coordinateDTO, Coordinate.class);
    }

}

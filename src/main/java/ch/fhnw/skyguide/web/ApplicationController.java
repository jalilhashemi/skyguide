package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.*;
import ch.fhnw.skyguide.persistence.*;
import ch.fhnw.skyguide.util.EmailSender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
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
    DrawingRepository drawingRepository;

    @Autowired
    DrawingTypeRepository drawingTypeRepository;

    @Autowired
    TimeRepository timeRepository;

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
            // activate mail sender
           // emailSender.send(application.getEmail(), application.getAdminKey(), application.getViewKey());
            return new ResponseEntity<>(convertToDto(application), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationDTO> findById(@PathVariable("id") String id) {
        Application application = applicationRepository.findByAdminKey(id);
        if(application != null)
            return new ResponseEntity<>(convertToDto(application), HttpStatus.OK);

        application = applicationRepository.findByKey(id);
        if(application != null)
            return new ResponseEntity<>(convertToDto(application), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ApplicationDTO convertToDto(Application application) {
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        applicationDTO.setActivityType(application.getActivityType().getName());
        applicationDTO.setAircraftType(application.getAircraftType().getName());
        applicationDTO.setHeightType(application.getHeightType().getName());

        List<DrawingDTO> drawingsDTO = new ArrayList<>();
        for (Drawing d : application.getDrawings())
            drawingsDTO.add(convertToDto(d));

        applicationDTO.setDrawings(drawingsDTO);

        List<TimeDTO> timesDTO = new ArrayList<>();
        for (Time t : application.getTimes())
            timesDTO.add(convertToDto(t));

        applicationDTO.setTimes(timesDTO);

        return applicationDTO;
    }

    private Application convertToEntity(ApplicationDTO applicationDTO) {
        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setActivityType(activityTypeRepository.findByName(applicationDTO.getActivityType()));
        application.setAircraftType(aircraftTypeRepository.findByName(applicationDTO.getAircraftType()));
        application.setHeightType(heightTypeRepository.findByName(applicationDTO.getHeightType()));

        Set<Drawing> drawings = new HashSet<>();
        for(DrawingDTO d : applicationDTO.getDrawings()) {
            Drawing drawing = drawingRepository.save(convertToEntity(d));
            drawings.add(drawing);
        }

        application.setDrawings(drawings);

        Set<Time> times = new HashSet<>();
        for(TimeDTO t : applicationDTO.getTimes()) {
            Time time = timeRepository.save(convertToEntity(t));
            times.add(time);
        }

        application.setTimes(times);

        return application;
    }

    private DrawingDTO convertToDto(Drawing drawing) {
        DrawingDTO drawingDTO = modelMapper.map(drawing, DrawingDTO.class);
        drawingDTO.setDrawingType(drawing.getDrawingType().getName());

        List<CoordinateDTO> coordinatesDTO = new ArrayList<>();
        for (Coordinate c : drawing.getCoordinates())
            coordinatesDTO.add(convertToDto(c));

        drawingDTO.setCoordinates(coordinatesDTO);

        return drawingDTO;
    }

    private Drawing convertToEntity(DrawingDTO  drawingDTO) {
        Drawing drawing = modelMapper.map(drawingDTO, Drawing.class);
        drawing.setDrawingType(drawingTypeRepository.findByName(drawingDTO.getDrawingType()));

        Set<Coordinate> coordinates = new HashSet<>();
        for(CoordinateDTO c : drawingDTO.getCoordinates()) {
            Coordinate coordinate = coordinateRepository.save(convertToEntity(c));
            coordinates.add(coordinate);
        }

        drawing.setCoordinates(coordinates);

        return drawing;
    }

    private CoordinateDTO convertToDto(Coordinate coordinate) {
        return modelMapper.map(coordinate, CoordinateDTO.class);
    }

    private Coordinate convertToEntity(CoordinateDTO coordinateDTO) {
        return modelMapper.map(coordinateDTO, Coordinate.class);
    }

    private TimeDTO convertToDto(Time time) {
        return modelMapper.map(time, TimeDTO.class);
    }

    private Time convertToEntity(TimeDTO timeDTO) {
        return modelMapper.map(timeDTO, Time.class);
    }

}

package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.*;
import ch.fhnw.skyguide.persistence.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/formtype")
public class FormTypeController {

    @Autowired
    FormTypeRepository formTypeRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    AircraftTypeRepository aircraftTypeRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<FormType>> findAll() {
        /*Field callsignFieldMandatory = fieldRepository.save(new Field("field_callsign","Callsign", "Callsign", true));
        Field callsignField = fieldRepository.save(new Field("field_callsign","Callsign", "Callsign", false));

        ActivityType airshowActivity = activityTypeRepository.findByName("Airshow");

        AircraftType rotaryAircraft = aircraftTypeRepository.findByName("Rotary Wing Aircraft");

        Set<Field> airshowRotaryFields = new HashSet<>();
        airshowRotaryFields.add(callsignFieldMandatory);
        FormType airshowRotary = formTypeRepository.save(new FormType(airshowActivity,rotaryAircraft, airshowRotaryFields));

        formTypeRepository.save(airshowRotary);

*/
        Iterable<FormType> formTypes = formTypeRepository.findAll();
        return new ResponseEntity<>(formTypes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<FormTypeDTO> create(@RequestBody FormTypeDTO formType) {
        FormType f = formTypeRepository.save(convertToEntity(formType));
        return new ResponseEntity<>(convertToDto(f), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, params = "new")
    public ResponseEntity<FormType> createNew() {
        Set<Field> fields = new HashSet<>();
        Field f = fieldRepository.save(new Field("testfield", "label", "placeholder", true));
        fields.add(f);
        ActivityType activityType = activityTypeRepository.save(new ActivityType());
        AircraftType aircraftType = aircraftTypeRepository.save(new AircraftType());
        FormType newType = new FormType(activityType, aircraftType, fields);
        formTypeRepository.save(newType);
        return new ResponseEntity<>(newType, HttpStatus.CREATED);
    }

    private FormTypeDTO convertToDto(FormType formType) {
        FormTypeDTO formTypeDTO = modelMapper.map(formType, FormTypeDTO.class);

        formTypeDTO.setActivityType(formType.getActivityType().getName());
        formTypeDTO.setAircraftType(formType.getAircraftType().getName());

        List<FieldDTO> fieldDTOS = new ArrayList<>();
        for (Field f : formType.getFields())
            fieldDTOS.add(convertToDto(f));

        formTypeDTO.setFields(fieldDTOS);

        return formTypeDTO;
    }

    private FormType convertToEntity(FormTypeDTO formTypeDTO) {
        FormType formType = modelMapper.map(formTypeDTO, FormType.class);
        formType.setActivityType(activityTypeRepository.findByName(formTypeDTO.getActivityType()));
        formType.setAircraftType(aircraftTypeRepository.findByName(formTypeDTO.getAircraftType()));

        Set<Field> fields = new HashSet<>();
        for (FieldDTO f : formTypeDTO.getFields()) {
            Field field = fieldRepository.save(convertToEntity(f));
            fields.add(field);
        }

        formType.setFields(fields);

        return formType;
    }

    private FieldDTO convertToDto(Field field) {
        return modelMapper.map(field, FieldDTO.class);
    }

    private Field convertToEntity(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, Field.class);
    }


/*
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<FormTypeDTO> create() {
        Set<Field> fields = new HashSet<>();
        fields.add(new Field("testfield", "label", "placeholder", true));
        FormType newType = new FormType(activityTypeRepository.findByName("Airshow"), aircraftTypeRepository.findByName("Rotary Wing Aircraft"), fields);
        formTypeRepository.save(newType);
        return new ResponseEntity<>(newType, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApplicationDTO> create(@RequestBody ApplicationDTO applicationDTO) {
        Application application = convertToEntity(applicationDTO);
        if (application != null) {
            application = applicationRepository.save(application);
            return new ResponseEntity<>(convertToDto(application), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationDTO> findById(@PathVariable("id") String id) {
        Application application = applicationRepository.findByAdminKey(id);
        if (application != null)
            return new ResponseEntity<>(convertToDto(application), HttpStatus.OK);

        application = applicationRepository.findByKey(id);
        if (application != null)
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
        for (DrawingDTO d : applicationDTO.getDrawings()) {
            Drawing drawing = drawingRepository.save(convertToEntity(d));
            drawings.add(drawing);
        }

        application.setDrawings(drawings);

        Set<Time> times = new HashSet<>();
        for (TimeDTO t : applicationDTO.getTimes()) {
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

    private Drawing convertToEntity(DrawingDTO drawingDTO) {
        Drawing drawing = modelMapper.map(drawingDTO, Drawing.class);
        drawing.setDrawingType(drawingTypeRepository.findByName(drawingDTO.getDrawingType()));

        Set<Coordinate> coordinates = new HashSet<>();
        for (CoordinateDTO c : drawingDTO.getCoordinates()) {
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
    */

}

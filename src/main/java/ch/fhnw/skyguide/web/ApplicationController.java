package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.ActivityType;
import ch.fhnw.skyguide.domain.AircraftType;
import ch.fhnw.skyguide.domain.Application;
import ch.fhnw.skyguide.domain.ApplicationDTO;
import ch.fhnw.skyguide.persistence.ActivityTypeRepository;
import ch.fhnw.skyguide.persistence.AircraftTypeRepository;
import ch.fhnw.skyguide.persistence.ApplicationRepository;
import ch.fhnw.skyguide.persistence.HeightTypeRepository;
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
import java.util.Date;

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
    EmailSender emailSender;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Application>> findAll() {
        Iterable<Application> applications = applicationRepository.findAll();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApplicationDTO> create(@RequestBody ApplicationDTO applicationDTO) {
        Application application = convertToEntity(applicationDTO);
        if (application != null) {
            Application appCreated = applicationRepository.save(application);
            application = applicationRepository.save(application);
            //emailSender.send();
            return new ResponseEntity<>(convertToDto(appCreated), HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Application> findById(@PathVariable("id") String id) {
        // return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(applicationRepository.findByAdminKey(id), HttpStatus.OK);
    }

    @RequestMapping(params = {"name", "company"}, method = RequestMethod.GET)
    public ResponseEntity<Application> createTemporaryTest(@RequestParam("name") String name, @RequestParam("company") String company) {
        Application app = new Application();
        app.setName(name);
        app.setCompany(company);
        app.setActivityType(activityTypeRepository.findByName("Calibration Flight"));
        try {
            Date date = new Date();

            String s = name + new Timestamp(date.getTime());
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            // System.out.println("byteData.length " + byteData.length);
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }

            app.setAdminKey(sb.toString());
        } catch (NoSuchAlgorithmException e) {

        }

        applicationRepository.save(app);
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

    private ApplicationDTO convertToDto(Application application) {
        ApplicationDTO applicationDTO = modelMapper.map(application, ApplicationDTO.class);
        applicationDTO.setActivityType(application.getActivityType().getName());
        //applicationDTO.setAircraftType(application.getAircraftType().getName());
      //  applicationDTO.setHeightType(application.getHeightType().getName());
        // TODO: coordinates
        return applicationDTO;
    }

    private Application convertToEntity(ApplicationDTO applicationDTO) {
        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setActivityType(activityTypeRepository.findByName(applicationDTO.getActivityType()));
      //  application.setAircraftType(aircraftTypeRepository.findByName(applicationDTO.getAircraftType()));
      //  application.setHeightType(heightTypeRepository.findByName(applicationDTO.getHeightType()));
        // TODO: coordinates
        return application;
    }

}

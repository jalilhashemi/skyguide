package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.Application;
import ch.fhnw.skyguide.persistence.ApplicationRepository;
import ch.fhnw.skyguide.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    EmailSender emailSender;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Application>> findAll() {
        Iterable<Application> applications = applicationRepository.findAll();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Application> create(@RequestBody Application application) {
        if(application != null) {
            application = applicationRepository.save(application);
            emailSender.send();
            return new ResponseEntity<>(application, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Application> findById(@PathVariable("id") String id) {
        return new ResponseEntity<>(applicationRepository.findOne(id), HttpStatus.OK);
    }

}

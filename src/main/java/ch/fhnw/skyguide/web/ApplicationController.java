package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.Application;
import ch.fhnw.skyguide.persistence.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Application> create(@RequestBody Application application) {
        if(application != null) {
            application = applicationRepository.save(application);
            return new ResponseEntity<>(application, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

}

package ch.fhnw.skyguide.web;

import ch.fhnw.skyguide.domain.Application;
import ch.fhnw.skyguide.persistence.ApplicationRepository;
import ch.fhnw.skyguide.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

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
        if (application != null) {
            application = applicationRepository.save(application);
            emailSender.send();
            return new ResponseEntity<>(application, HttpStatus.CREATED);
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
        } catch (NoSuchAlgorithmException e){

        }

        applicationRepository.save(app);
        return new ResponseEntity<>(app, HttpStatus.OK);
    }

}

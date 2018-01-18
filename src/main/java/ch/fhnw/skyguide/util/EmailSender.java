package ch.fhnw.skyguide.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {
    @Value("${skyguide.mail}")
    private String adminMail;

    @Autowired
    private JavaMailSender sender;

    public boolean send(String applicantEmail, String adminKey, String viewKey) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(adminMail);
            helper.setSubject("There is a new Application");
            helper.setText("Link: http://localhost:8080?key="+adminKey +"&edit");

            sender.send(message);

            helper.setTo(applicantEmail);
            helper.setSubject("Your Application to skyguide");
            helper.setText("Link: http://localhost:8080/applications/"+viewKey);

            sender.send(message);

            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}

package ch.fhnw.skyguide.util;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;



@Component
public class EmailSender {
    @Autowired
    private JavaMailSender sender;

    public boolean send() {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(new String[]{"jalil.hashemi@students.fhnw.ch", "marco.ghilardelli@students.fhnw.ch"});
            helper.setText("Link");
            helper.setSubject("There is a new Application");

            sender.send(message);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}

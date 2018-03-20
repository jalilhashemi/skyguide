package ch.fhnw.skyguide.util;

import ch.fhnw.skyguide.domain.Application;
import ch.fhnw.skyguide.domain.Coordinate;
import ch.fhnw.skyguide.domain.Drawing;
import ch.fhnw.skyguide.domain.Time;
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

    public boolean send(Application application) {
        //String applicantEmail, String adminKey, String viewKey) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(adminMail);
            helper.setSubject("There is a new Application");

            String drawings = "";
            for (Drawing d : application.getDrawings()) {
                drawings += d.getDrawingType().getName() + ":";
                for (Coordinate c : d.getCoordinates()) {
                    drawings += c.getLat() + ", " + c.getLon() + ";";
                }
                drawings += "\n";
            }

            String times = "";
            for (Time t : application.getTimes()) {
                times += t.getStart() + " - " + t.getEnd() + "\n";
            }

            helper.setText("Link: http://localhost:8080?key=" + application.getAdminKey() + "&edit\n\n" +
                    "Activity Type:\t" + application.getActivityType().getName() + "\n" +
                    "Aircraft Type:\t" + application.getAircraftType().getName() + "\n" +
                    "name:\t\t" + application.getName() + "\n" +
                    "company:\t" + application.getCompany() + "\n" +
                    "reference:\t" + application.getReference() + "\n" +
                    "phone:\t" + application.getPhone() + "\n" +
                    "email:\t\t" + application.getEmail() + "\n" +
                    "callsign:\t\t" + application.getCallsign() + "\n" +
                    "departure:\t" + application.getDeparture() + "\n" +
                    "destination:\t" + application.getDestination() + "\n" +
                    "date:\t\t" + application.getDateFromUntil() + "\n" +
                    "duration:\t\t" + application.getDuration() + "\n" +
                    "location:\t\t" + application.getLocation() + "\n" +
                    "beam dir:\t" + application.getBeamDirection() + "\n" +
                    "payload:\t\t" + application.getPayloadAttachedObj() + "\n" +
                    "amount:\t\t" + application.getAmount() + "\n" +
                    "radius:\t\t" + application.getRadius() + "\n" +
                    "self declaration:\t" + application.getSelfDeclaration() + "\n" +
                    "remark:\t\t" + application.getRemark() + "\n" +
                    "height:\t\t" + application.getHeightAltitude() + " " + application.getHeightType().getName() + "\n" +
                    "drawings:\n" + drawings +
                    "times:\n" + times
            );
        sender.send(message);

        helper.setTo(application.getEmail());
        helper.setSubject("Your Application to skyguide");
        helper.setText("Link: http://localhost:8080?key=" + application.getViewKey());

        sender.send(message);

        return true;
    }
        catch(
    Exception e)

    {
        return false;
    }
}
}

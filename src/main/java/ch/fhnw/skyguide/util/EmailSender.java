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
            helper.setSubject("There is a new Application (Beta)");

            String drawings = "";
            for (Drawing d : application.getDrawings()) {
                drawings += d.getDrawingType().getName() + ": ";
                for (Coordinate c : d.getCoordinates()) {
                    drawings += "\t" + c.getLat() + ", " + c.getLon() + ";";
                }
                drawings += "\n";
            }

            String times = "";
            for (Time t : application.getTimes()) {
                times += "\t" + t.getStart() + " - " + t.getEnd() + "\n";
            }

            helper.setText(
                    "Activity Type:\t" + checkInput(application.getActivityType().getName()) + "\n" +
                            "Aircraft Type:\t" + checkInput(application.getAircraftType().getName()) + "\n" +
                            "name:\t\t" + checkInput(application.getName()) + "\n" +
                            "company:\t" + checkInput(application.getCompany()) + "\n" +
                            "reference:\t" + checkInput(application.getReference()) + "\n" +
                            "phone:\t\t" + checkInput(application.getPhone()) + "\n" +
                            "email:\t\t" + checkInput(application.getEmail()) + "\n" +
                            "callsign:\t\t" + checkInput(application.getCallsign()) + "\n" +
                            "departure:\t" + checkInput(application.getDeparture()) + "\n" +
                            "destination:\t" + checkInput(application.getDestination()) + "\n" +
                            "date:\t\t" + checkInput(application.getDateFromUntil()) + "\n" +
                            "duration:\t\t" + checkInput(application.getDuration()) + "\n" +
                            "location:\t\t" +checkInput( application.getLocation()) + "\n" +
                            "beam dir:\t\t" + checkInput(application.getBeamDirection()) + "\n" +
                            "payload:\t\t" +checkInput( application.getPayloadAttachedObj()) + "\n" +
                            "amount:\t\t" + checkInput(application.getAmount()) + "\n" +
                            "radius:\t\t" + checkInput(application.getRadius()) + "\n" +
                            "self decl:\t\t" + checkInput(application.getSelfDeclaration()) + "\n" +
                            "remark:\t\t" + checkInput(application.getRemark()) + "\n" +
                            "height:\t\t" + application.getHeightAltitude() + " " + application.getHeightType().getName() + "\n" +
                            "drawings:\n\t" + drawings +
                            "times:\n" + times +
                            "\n\nhttp://localhost:8080?key=" + application.getAdminKey() + "&edit\n"
            );
            sender.send(message);

            helper.setTo(application.getEmail());
            helper.setSubject("Your Application to skyguide");
            helper.setText("Link: http://localhost:8080?key=" + application.getViewKey());

            sender.send(message);

            return true;
        } catch (
                Exception e)

        {
            return false;
        }
    }

    public String checkInput(String in) {
        if (in == "" || in == null)
            return "-";
        else
            return in;
    }
}

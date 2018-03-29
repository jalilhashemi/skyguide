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
                    "Activity Type:\t" + application.getActivityType().getName() + "\n" +
                            printAttr("Aircraft Type:\t", application.getAircraftType().getName()) +
                            "name:\t\t" + application.getName() + "\n" +
                            printAttr("company:\t", application.getCompany()) +
                            printAttr("reference:\t", application.getReference()) +
                            "phone:\t\t" + formatPhone(application.getPhone()) + "\n" +
                            "email:\t\t" + application.getEmail() + "\n" +
                            printAttr("callsign:\t\t", application.getCallsign()) +
                            printAttr("departure:\t", application.getDeparture()) +
                            printAttr("destination:\t", application.getDestination()) +
                            printAttr("date:\t\t", application.getDateFromUntil()) +
                            printAttr("duration:\t\t", application.getDuration() + " h") +
                            printAttr("location:\t\t", application.getLocation()) +
                            printAttr("beam dir:\t\t", application.getBeamDirection()) +
                            printAttr("payload:\t\t", application.getPayloadAttachedObj()) +
                            printAttr("amount:\t\t", application.getAmount()) +
                            printAttr("radius:\t\t", application.getRadius()) +
                            printAttr("self decl:\t\t", application.getSelfDeclaration()) +
                            printAttr("remark:\t\t", application.getRemark()) +
                            printAttr("height:\t\t", application.getHeightAltitude() + " " + application.getHeightType().getName()) +
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

    public String formatPhone(String number) {
        if(number.charAt(3) == ' ') {
            return number;
        }
        else {
            if (number.charAt(0) == '+')
                return number.substring(0, 3) + " " + number.substring(3, 5) + " "
                        + number.substring(5, 8) + " " + number.substring(8, 10) + " " + number.substring(10, 12);
            else
                return number.substring(0, 3) + " " + number.substring(3, 6) + " "
                        + number.substring(6, 8) + " " + number.substring(8, 10);
        }
    }

    public String printAttr(String attrName, String val) {
        if (val == "") {
            return "";

        } else {
            return attrName + val + "\n";
        }

    }
}

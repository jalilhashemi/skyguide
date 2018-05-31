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

    @Value("${test.mail}")
    private String testMail;

    @Autowired
    private JavaMailSender sender;

    public boolean send(Application application) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            String[] mails = { adminMail, testMail};
            helper.setTo(mails);
            helper.setSubject("There is a new Application (Beta)");
            String drawings = "";
            for (Drawing d : application.getDrawings()) {
                drawings += d.getDrawingType().getName() + ": ";
                if (d.getRadius() != null)
                    drawings += "Radius: " + d.getRadius() + "\n";
                drawings += "Height: " + d.getAltitude() + "\n";
                for (Coordinate c : d.getCoordinates()) {
                    drawings += "\t" + c.getLat() + ", " + c.getLon() + ";";
                }
                drawings += "<br>";
            }

            String times = "";
            for (Time t : application.getTimes()) {
                times += "\t" + t.getStart().toString().substring(0, 5) + " - " + t.getEnd().toString().substring(0, 5) + "<br>";
            }

            //helper.setText(
            String htmlMsg = "<table style=\"border-collapse: collapse\">" +
                    printAttr("Activity Type", application.getActivityType().getName()) +
                    printAttr("Aircraft Type", application.getAircraftType().getName()) +
                    printAttr("Name", application.getName()) +
                    printAttr("Company", application.getCompany()) +
                    printAttr("Reference", application.getReference()) +
                    printAttr("Phone", formatPhone(application.getPhone())) +
                    printAttr("Email", application.getEmail()) +
                    printAttr("Callsign", application.getCallsign()) +
                    printAttr("Departure", application.getDeparture()) +
                    printAttr("Destination", application.getDestination()) +
                    printAttr("Date", application.getDateFromUntil()) +
                    printAttr("Duration", application.getDuration() + " h") +
                    printAttr("Location", application.getLocation()) +
                    printAttr("Beam direction", application.getBeamDirection()) +
                    printAttr("Payload", application.getPayloadAttachedObj()) +
                    printAttr("Amount", application.getAmount()) +
                    // printAttr("Radius", application.getRadius()) +
                    printAttr("Self declaration", application.getSelfDeclaration()) +
                    printAttr("Remark", application.getRemark()) +
                    //  printAttr("Height", application.getHeightAltitude() + " " + application.getHeightType().getName()) +
                    printAttr("Coordinates & Drawings", drawings) +
                    printAttr("Time schedule", times) +
                    printAttr("Are the altitudes flexible or not?", application.getFreeAnswer1()) +
                    printAttr("Is it possible to interrupt the mission?", application.getFreeAnswer2()) +
                    printAttr("Is the mission dependent on a certain time frame, e.g. due to the position of the sun?", application.getFreeAnswer3()) +
                    printAttr("Is the mission dependent on certain conditions (no clouds, no snow, no leaves)?", application.getFreeAnswer4()) +
                    "</table>";

                    // Admin Link
                   // "<p>" + "Link to the application: http://localhost:8080?key=" + application.getViewKey() + "</p>";

            message.setContent(htmlMsg, "text/html");
            sender.send(message);

            // send User Mail with Link
           /* helper.setTo(application.getEmail());
            helper.setSubject("Your Application to skyguide");
            helper.setText("http://localhost:8080?key=" + application.getAdminKey() + "&edit\n");
            sender.send(message);*/

            return true;
        } catch (
                Exception e) {
            return false;
        }
    }

    public String formatPhone(String number) {
        return number.substring(0, 3) + " " + number.substring(3, 6) + " "
                + number.substring(6, 8) + " " + number.substring(8, 10);
    }

    public String printAttr(String attrName, String val) {
        if (val == "") {
            return "";
        }
        if (val == null) {
            return "";
        } else {
            //return attrName + val + "\n";
            return "<tr>\n" +
                    "<b><td style=\"width:20%; border: 1px solid black; padding: 5px; text-align: left;\">" + attrName + "</td></b>" +
                    "<td style=\"width:80%; border: 1px solid black; padding: 5px; text-align: left;\">" + val + "</td>" +
                    "</tr>";
        }
    }
}

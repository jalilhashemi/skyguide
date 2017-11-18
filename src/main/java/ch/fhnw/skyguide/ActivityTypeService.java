package ch.fhnw.skyguide;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ActivityTypeService {

    private List<ActivityType> list = new ArrayList<>();

    public ActivityType getAircraftTypeByName(String name) {
        Predicate<ActivityType> aircraftTypePredicate = a -> a.getName().equals(name);
        ActivityType obj = list.stream().filter(aircraftTypePredicate).findFirst().get();
        return obj;
    }

    public List<ActivityType> getAllAircraftTypes() {
        return list;
    }

    {
        Field callsignFieldMandatory = new Field("input_callsign", true, true);
        Field callsignFieldActive = new Field("input_callsign", false, true);
        Field callsignFieldInactive = new Field("input_callsign", false, false);

        Field depDestField = new Field("input_dep_dest", false, false);
        Field dateFromField = new Field("input_date_from", true, true);

        ActivityType skyLaternActivityType = new ActivityType("Sky Latern");
        skyLaternActivityType.getFieldList().add(callsignFieldInactive);
        skyLaternActivityType.getFieldList().add(depDestField);
        skyLaternActivityType.getFieldList().add(dateFromField);
        list.add(skyLaternActivityType);

        ActivityType weatherBalloonActivityType = new ActivityType("Weather Balloon");
        weatherBalloonActivityType.getFieldList().add(callsignFieldInactive);
        weatherBalloonActivityType.getFieldList().add(depDestField);
        weatherBalloonActivityType.getFieldList().add(dateFromField);
        list.add(weatherBalloonActivityType);

        ActivityType hotAirBalloonActivityType = new ActivityType("Hot Air Balloon");
        hotAirBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(depDestField);
        hotAirBalloonActivityType.getFieldList().add(dateFromField);
        list.add(hotAirBalloonActivityType);


    }
}
package ch.fhnw.skyguide;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AircraftTypeService {

    public AircraftType getAircraftTypeByName(String name) {
        Predicate<AircraftType> aircraftTypePredicate = a-> a.getName().equals(name);
        AircraftType obj = list.stream().filter(aircraftTypePredicate).findFirst().get();
        return obj;
    }

    public List<AircraftType> getAllAircraftTypes() {
        return list;
    }

    private List<AircraftType> list = new ArrayList<>();

    {
        Field dateField = new Field("date", true, true);
        Field timeField = new Field("time", true, false);

        AircraftType a = new AircraftType("Sky Latern");
        a.getFieldList().add(dateField);
        a.getFieldList().add(timeField);
        list.add(a);
    }
}
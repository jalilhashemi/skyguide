package ch.fhnw.skyguide;

import java.util.ArrayList;
import java.util.List;

public class ActivityType {
    private String name;
    //private List<Field> fieldList;
    private List<AircraftType> aircraftTypesList;

    public ActivityType(String name) {
        this.name = name;
        //fieldList = new ArrayList<>();
        aircraftTypesList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Field> getFieldList() {
//        return fieldList;
//    }
//
//    public void setFieldList(List<Field> fieldList) {
//        this.fieldList = fieldList;
//    }

    public List<AircraftType> getAircraftTypesList() {
        return aircraftTypesList;
    }

    public void setAircraftTypesList(List<AircraftType> aircraftTypesList) {
        this.aircraftTypesList = aircraftTypesList;
    }
}
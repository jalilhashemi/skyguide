package ch.fhnw.skyguide.service;

import java.util.ArrayList;
import java.util.List;

public class ActivityType {
    private String name;
    private List<AircraftType> aircraftTypeList;

    public ActivityType(String name) {
        this.name = name;
        aircraftTypeList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AircraftType> getAircraftTypeList() {
        return aircraftTypeList;
    }

    public void setAircraftTypeList(List<AircraftType> aircraftTypeList) {
        this.aircraftTypeList = aircraftTypeList;
    }
}
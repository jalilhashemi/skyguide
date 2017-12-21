package ch.fhnw.skyguide.service;

import java.util.ArrayList;
import java.util.List;

public class ActivityType {
    private String name;
    private String label;
    private List<AircraftType> aircraftTypeList;

    public ActivityType(String label) {
        this.name = "activityType";
        this.label = label;
        aircraftTypeList = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public List<AircraftType> getAircraftTypeList() {
        return aircraftTypeList;
    }

    public void setAircraftTypeList(List<AircraftType> aircraftTypeList) {
        this.aircraftTypeList = aircraftTypeList;
    }
}
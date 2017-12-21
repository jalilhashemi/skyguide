package ch.fhnw.skyguide.service;

import java.util.ArrayList;
import java.util.List;

public class AircraftType {
    private String name;
    private String label;
    private List<Field> fieldList;

    public AircraftType(String label) {
        this.name = "aircraftType";
        this.label = label;
        fieldList = new ArrayList<>();
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

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}

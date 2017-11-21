package ch.fhnw.skyguide;

import java.util.ArrayList;
import java.util.List;

public class AircraftType {
    private String name;
    private List<Field> fieldList;

    public AircraftType(String name) {
        this.name = name;
        fieldList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}

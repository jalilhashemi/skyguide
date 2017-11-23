package ch.fhnw.skyguide;

import java.util.ArrayList;
import java.util.List;

public class HeightAltitudeField {
    private String name;
    private boolean isMandatory;
    private boolean isActive;
    private List<Field> fieldList;

    public HeightAltitudeField(String name, boolean mandatory, boolean active) {
        this.name = name;
        this.isMandatory = mandatory;
        this.isActive = active;
        fieldList = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}

package ch.fhnw.skyguide;
import com.fasterxml.jackson.annotation.JsonView;

public class Field {
    private String name;
    private boolean isMandatory;
    private boolean isActive;

    public Field(String name, boolean mandatory, boolean active) {
        this.name = name;
        this.isMandatory = mandatory;
        this.isActive = active;
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
}
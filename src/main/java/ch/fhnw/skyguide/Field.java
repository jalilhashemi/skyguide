package ch.fhnw.skyguide;

public class Field {
    private String id;
    private String name;
    private boolean isMandatory;
    private boolean isActive;

    public Field(String id, String name, boolean mandatory, boolean active) {
        this.id = id;
        this.name = name;
        this.isMandatory = mandatory;
        this.isActive = active;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
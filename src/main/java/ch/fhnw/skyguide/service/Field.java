package ch.fhnw.skyguide.service;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private String id;
    private String name;
    private String placeholder;
    private String tooltip;
    private boolean isMandatory;
    private boolean isActive;
    private List<Field> options;

    public Field(String id, String name, String placeholder, boolean mandatory, boolean active) {
        this.id = id;
        this.name = name;
        this.placeholder = placeholder;
        this.isMandatory = mandatory;
        this.isActive = active;
        options = new ArrayList<>();

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

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
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

    public List<Field> getOptions() {
        return options;
    }

    public void setOptions(List<Field> options) {
        this.options = options;
    }
}
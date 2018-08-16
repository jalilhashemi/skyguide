package ch.fhnw.skyguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.text.Normalizer;
import java.util.Set;

@Entity
public class FormType {

    private Integer id;
    private ActivityType activityType;
    private AircraftType aircraftType;
    private Set<Field> fields;

    public FormType() {

    }

    public FormType(ActivityType activityType, Set<Field> fields) {
        this.activityType = activityType;
        this.aircraftType = null;
        this.fields = fields;
    }

    public FormType(ActivityType activityType, AircraftType aircraftType, Set<Field> fields) {
        this.activityType = activityType;
        this.aircraftType = aircraftType;
        this.fields = fields;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @ManyToOne
    @JoinColumn(name = "aircraft_type_id")
    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    @ManyToMany
    @JoinTable(name = "form_type_field", joinColumns = @JoinColumn(name = "form_type_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"))
    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }
}

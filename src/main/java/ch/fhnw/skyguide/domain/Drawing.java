package ch.fhnw.skyguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Drawing {

    private Integer id;
    private DrawingType drawingType;
    private Set<Coordinate> coordinates;
    private Set<Application> applications;

    public Drawing() {

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
    @JoinColumn(name = "drawing_type_id")
    public DrawingType getDrawingType() {
        return drawingType;
    }

    public void setDrawingType(DrawingType drawingType) {
        this.drawingType = drawingType;
    }

    @ManyToMany
    @JoinTable(name = "drawing_coordinate", joinColumns = @JoinColumn(name = "drawing_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "coordinate_id", referencedColumnName = "id"))
    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Set<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @ManyToMany(mappedBy = "drawings")
    @JsonIgnore
    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}

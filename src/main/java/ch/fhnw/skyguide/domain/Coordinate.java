package ch.fhnw.skyguide.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Coordinate {

    private Integer id;
    private Float latitude;
    private Float longitude;
    private Set<Application> applications;

    public Coordinate() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @ManyToMany(mappedBy = "coordinates")
    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}

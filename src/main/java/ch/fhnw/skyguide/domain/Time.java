package ch.fhnw.skyguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Time {

    private Integer id;
    private java.sql.Time start;
    private java.sql.Time end;
    private Set<Application> applications;

    public Time() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.sql.Time getStart() {
        return start;
    }

    public void setStart(java.sql.Time start) {
        this.start = start;
    }

    public java.sql.Time getEnd() {
        return end;
    }

    public void setEnd(java.sql.Time end) {
        this.end = end;
    }

    @ManyToMany(mappedBy = "times")
    @JsonIgnore
    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}

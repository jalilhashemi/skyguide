package ch.fhnw.skyguide.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Table(name = "activity_type")
public class ActivityType {

    private Integer id;
    private String name;
    private Set<Application> applications;

    public ActivityType(){}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany
    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}


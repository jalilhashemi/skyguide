package ch.fhnw.skyguide.domain;

import javax.persistence.*;

@Entity
//@Table(name = "activity_type")
public class ActivityType {

    private Integer id;
    private String name;
    private Application application;

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

    @OneToOne(mappedBy = "activityType")
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}


package ch.fhnw.skyguide.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Table(name = "height_type")
public class HeightType {

    private Integer id;
    private String name;
    private Set<Application> application;

    public HeightType(){
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @OneToMany(mappedBy = "heightType")
    public Set<Application> getApplication() {
        return application;
    }

    public void setApplication(Set<Application> application) {
        this.application = application;
    }
}

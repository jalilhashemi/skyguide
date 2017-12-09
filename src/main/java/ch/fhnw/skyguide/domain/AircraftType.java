package ch.fhnw.skyguide.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AircraftType extends Height{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;


    public AircraftType(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

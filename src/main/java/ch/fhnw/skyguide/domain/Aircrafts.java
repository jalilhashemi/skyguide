package ch.fhnw.skyguide.domain;

import javax.persistence.*;

@Entity
public class Aircrafts extends Height{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String fixedWingAircraft;
    private String rotaryWingAircraft;
    private String rpas;

    public Aircrafts(){}

    public Aircrafts (int id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

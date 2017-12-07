package ch.fhnw.skyguide.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Aircrafts {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String fixedWingAircraft;
    private String rotaryWingAircraft;
    private String rpas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFixedWingAircraft() {
        return fixedWingAircraft;
    }

    public void setFixedWingAircraft(String fixedWingAircraft) {
        this.fixedWingAircraft = fixedWingAircraft;
    }

    public String getRotaryWingAircraft() {
        return rotaryWingAircraft;
    }

    public void setRotaryWingAircraft(String rotaryWingAircraft) {
        this.rotaryWingAircraft = rotaryWingAircraft;
    }

    public String getRpas() {
        return rpas;
    }

    public void setRpas(String rpas) {
        this.rpas = rpas;
    }
}

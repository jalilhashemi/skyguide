package ch.fhnw.skyguide.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Float latitude;
    private Float longitude;
}

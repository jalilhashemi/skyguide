package ch.fhnw.skyguide.domain;

import javax.persistence.*;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Float latitude;
    private Float longitude;
}

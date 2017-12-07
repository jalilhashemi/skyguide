package ch.fhnw.skyguide.domain;

import javax.persistence.*;

@Entity
public class Height {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer fl;
    private Integer ftAmsl;
    private Integer mGnd;
}

package ch.fhnw.skyguide.domain;

import javax.persistence.*;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Float latitude;
    private Float longitude;

    //private Application application;

   public Coordinate(){

   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

//    @ManyToOne
//    @JoinColumn(name = "")
//    public Application getApplication() {
//        return application;
//    }
//
//    public void setApplication(Application application) {
//        this.application = application;
//    }
}

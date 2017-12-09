package ch.fhnw.skyguide.domain;

import javax.persistence.*;

@Entity
public class Height extends Coordinate{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer fl;
    private Integer ftAmsl;
    private Integer mGnd;
//    private Application application;

    public Height(){
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFl() {
        return fl;
    }

    public void setFl(Integer fl) {
        this.fl = fl;
    }

    public Integer getFtAmsl() {
        return ftAmsl;
    }

    public void setFtAmsl(Integer ftAmsl) {
        this.ftAmsl = ftAmsl;
    }

    public Integer getmGnd() {
        return mGnd;
    }

    public void setmGnd(Integer mGnd) {
        this.mGnd = mGnd;
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

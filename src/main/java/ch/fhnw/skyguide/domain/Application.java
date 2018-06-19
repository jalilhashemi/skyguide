package ch.fhnw.skyguide.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@JsonIgnoreProperties({"id", "viewKey", "adminKey"})
@Entity
public class Application {

    private Integer id;
    private String viewKey;
    private String adminKey;
    private String name;
    private String company;
//    private String reference;
    private String phone;
    private String email;
    private String callsign;
    private String departure;
    private String destination;
    private String dateFromUntil;
    private String duration;
    private String location;
    private String beamDirection;
    private String payloadAttachedObj;
    private String amount;
    private String selfDeclaration;
    private String remark;
    private ActivityType activityType;
    private AircraftType aircraftType;
    private HeightType heightType;
    private Set<Drawing> drawings;
    private Set<Time> times;
    private String freeAnswer1;
    private String freeAnswer2;
    private String freeAnswer3;
    private String freeAnswer4;
    private String filltime;


    public Application() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(String adminKey) {
        this.adminKey = adminKey;
    }

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

//    public String getReference() {
//        return reference;
//    }

//    public void setReference(String reference) {
//        this.reference = reference;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateFromUntil() {
        return dateFromUntil;
    }

    public void setDateFromUntil(String dateFromUntil) {
        this.dateFromUntil = dateFromUntil;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBeamDirection() {
        return beamDirection;
    }

    public void setBeamDirection(String beamDirection) {
        this.beamDirection = beamDirection;
    }

    public String getPayloadAttachedObj() {
        return payloadAttachedObj;
    }

    public void setPayloadAttachedObj(String payloadAttachedObj) {
        this.payloadAttachedObj = payloadAttachedObj;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSelfDeclaration() {
        return selfDeclaration;
    }

    public void setSelfDeclaration(String selfDeclaration) {
        this.selfDeclaration = selfDeclaration;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFreeAnswer1() {
        return freeAnswer1;
    }

    public void setFreeAnswer1(String freeAnswer1) {
        this.freeAnswer1 = freeAnswer1;
    }

    public String getFreeAnswer2() {
        return freeAnswer2;
    }

    public void setFreeAnswer2(String freeAnswer2) {
        this.freeAnswer2 = freeAnswer2;
    }

    public String getFreeAnswer3() {
        return freeAnswer3;
    }

    public void setFreeAnswer3(String freeAnswer3) {
        this.freeAnswer3 = freeAnswer3;
    }

    public String getFreeAnswer4() {
        return freeAnswer4;
    }

    public void setFreeAnswer4(String freeAnswer4) {
        this.freeAnswer4 = freeAnswer4;
    }

    public String getFilltime() {
        return filltime;
    }

    public void setFilltime(String filltime) {
        this.filltime = filltime;
    }

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @ManyToOne
    @JoinColumn(name = "aircraft_type_id")
    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    @ManyToOne
    @JoinColumn(name = "height_type_id")
    public HeightType getHeightType() {
        return heightType;
    }

    public void setHeightType(HeightType heightType) {
        this.heightType = heightType;
    }

    @ManyToMany
    @JoinTable(name = "application_drawing", joinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "drawing_id", referencedColumnName = "id"))
    public Set<Drawing> getDrawings() {
        return drawings;
    }

    public void setDrawings(Set<Drawing> drawings) {
        this.drawings = drawings;
    }

    @ManyToMany
    @JoinTable(name = "application_time", joinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "time_id", referencedColumnName = "id"))
    public Set<Time> getTimes() {
        return times;
    }

    public void setTimes(Set<Time> times) {
        this.times = times;
    }
}

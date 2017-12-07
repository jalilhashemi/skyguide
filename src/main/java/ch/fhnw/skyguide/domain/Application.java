package ch.fhnw.skyguide.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String company;
    private String reference;
    private Integer phone;
    private String email;
    private String callsign;
    private String departure;
    private String destination;
    private String dateFromUntil;
    private String timeFrom;
    private String timeUntil;
    private String duration;
    private String location;
    private String beamDirection;
    private String payloadAttachedObj;
    private String amount;
    private String radius;
    private String selfDeclaration;
    private String remark;
//    private Set<Activities> activities;
//    private Set<Aircrafts> aircrafts;
//    private Set<Coordinate> coordinate;
//    private Set<Height> height;

    public Application(){

    }

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
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

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeUntil() {
        return timeUntil;
    }

    public void setTimeUntil(String timeUntil) {
        this.timeUntil = timeUntil;
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

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
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

//    @OneToMany(mappedBy = "Application", cascade = CascadeType.ALL)
//    public Set<Activities> getActivities() {
//        return activities;
//    }
//    public void setActivities(Set<Activities> activities) {
//        this.activities = activities;
//    }
//    @OneToMany(mappedBy = "Application", cascade = CascadeType.ALL)
//    public Set<Aircrafts> getAircrafts() {
//        return aircrafts;
//    }
//
//    public void setAircrafts(Set<Aircrafts> aircrafts) {
//        this.aircrafts = aircrafts;
//    }
//
//    @OneToMany(mappedBy = "Application", cascade = CascadeType.ALL)
//    public Set<Coordinate> getCoordinate() {
//        return coordinate;
//    }
//
//    public void setCoordinate(Set<Coordinate> coordinate) {
//        this.coordinate = coordinate;
//    }
//
//    @OneToMany(mappedBy = "Application", cascade = CascadeType.ALL)
//    public Set<Height> getHeight() {
//        return height;
//    }
//
//    public void setHeight(Set<Height> height) {
//        this.height = height;
//    }
//
//    @Override
//    public String toString() {
//        String result = String.format(
//                "Category[id=%d, name='%s']%n",
//                id, name);
//        if (activities != null) {
//            for(Activities act : activities) {
//                result += String.format(
//                        "Book[id=%d, name='%s']%n",
//                        act.getId(), act.getId());
//            }
//        }
//
//        return result;
//    }
}

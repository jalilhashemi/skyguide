package ch.fhnw.skyguide.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activities {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String airshow;
    private String calibrationFlight;
    private String captive;
    private String contest;
    private String firework;
    private String flightWithHangGlider;
    private String gasBalloon;
    private String gotAirBalloon;
    private String kite;
    private String militaryMission;
    private String parachute;
    private String photoFlight;
    private String policeMission;
    private String skyLantern;
    private String skyLightLase;
    private String surveyFlight;
    private String tVRelayFlight;
    private String taxiPassengerFlight;
    private String testFlight;
    private String toyBalloon;
    private String transportFlight;
    private String weatherBalloon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirshow() {
        return airshow;
    }

    public void setAirshow(String airshow) {
        this.airshow = airshow;
    }

    public String getCalibrationFlight() {
        return calibrationFlight;
    }

    public void setCalibrationFlight(String calibrationFlight) {
        this.calibrationFlight = calibrationFlight;
    }

    public String getCaptive() {
        return captive;
    }

    public void setCaptive(String captive) {
        this.captive = captive;
    }

    public String getContest() {
        return contest;
    }

    public void setContest(String contest) {
        this.contest = contest;
    }

    public String getFirework() {
        return firework;
    }

    public void setFirework(String firework) {
        this.firework = firework;
    }

    public String getFlightWithHangGlider() {
        return flightWithHangGlider;
    }

    public void setFlightWithHangGlider(String flightWithHangGlider) {
        this.flightWithHangGlider = flightWithHangGlider;
    }

    public String getGasBalloon() {
        return gasBalloon;
    }

    public void setGasBalloon(String gasBalloon) {
        this.gasBalloon = gasBalloon;
    }

    public String getGotAirBalloon() {
        return gotAirBalloon;
    }

    public void setGotAirBalloon(String gotAirBalloon) {
        this.gotAirBalloon = gotAirBalloon;
    }

    public String getKite() {
        return kite;
    }

    public void setKite(String kite) {
        this.kite = kite;
    }

    public String getMilitaryMission() {
        return militaryMission;
    }

    public void setMilitaryMission(String militaryMission) {
        this.militaryMission = militaryMission;
    }

    public String getParachute() {
        return parachute;
    }

    public void setParachute(String parachute) {
        this.parachute = parachute;
    }

    public String getPhotoFlight() {
        return photoFlight;
    }

    public void setPhotoFlight(String photoFlight) {
        this.photoFlight = photoFlight;
    }

    public String getPoliceMission() {
        return policeMission;
    }

    public void setPoliceMission(String policeMission) {
        this.policeMission = policeMission;
    }

    public String getSkyLantern() {
        return skyLantern;
    }

    public void setSkyLantern(String skyLantern) {
        this.skyLantern = skyLantern;
    }

    public String getSkyLightLase() {
        return skyLightLase;
    }

    public void setSkyLightLase(String skyLightLase) {
        this.skyLightLase = skyLightLase;
    }

    public String getSurveyFlight() {
        return surveyFlight;
    }

    public void setSurveyFlight(String surveyFlight) {
        this.surveyFlight = surveyFlight;
    }

    public String gettVRelayFlight() {
        return tVRelayFlight;
    }

    public void settVRelayFlight(String tVRelayFlight) {
        this.tVRelayFlight = tVRelayFlight;
    }

    public String getTaxiPassengerFlight() {
        return taxiPassengerFlight;
    }

    public void setTaxiPassengerFlight(String taxiPassengerFlight) {
        this.taxiPassengerFlight = taxiPassengerFlight;
    }

    public String getTestFlight() {
        return testFlight;
    }

    public void setTestFlight(String testFlight) {
        this.testFlight = testFlight;
    }

    public String getToyBalloon() {
        return toyBalloon;
    }

    public void setToyBalloon(String toyBalloon) {
        this.toyBalloon = toyBalloon;
    }

    public String getTransportFlight() {
        return transportFlight;
    }

    public void setTransportFlight(String transportFlight) {
        this.transportFlight = transportFlight;
    }

    public String getWeatherBalloon() {
        return weatherBalloon;
    }

    public void setWeatherBalloon(String weatherBalloon) {
        this.weatherBalloon = weatherBalloon;
    }
}

package ch.fhnw.skyguide;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ActivityTypeService {

    private List<ActivityType> listActivityType = new ArrayList<>();

    public ActivityType getActivityTypeByName(String name) {
        Predicate<ActivityType> activityTypePredicate = a -> a.getName().equals(name);
        ActivityType obj = listActivityType.stream().filter(activityTypePredicate).findFirst().get();
        return obj;
    }

    public List<ActivityType> getAllActivityTypes() {
        return listActivityType;
    }

    private List<AircraftType> listAircraftType = new ArrayList<>();

    public AircraftType getAircraftTypeByName(String name) {
        Predicate<AircraftType> aircraftTypePredicate = a -> a.getName().equals(name);
        AircraftType obj = listAircraftType.stream().filter(aircraftTypePredicate).findFirst().get();
        return obj;
    }

    public List<AircraftType> getAllAircraftTypes() {
        return listAircraftType;
    }

    {
        Field callsignFieldMandatory = new Field("div_callsign", true, true);
        Field callsignFieldActive = new Field("div_callsign", false, true);
        Field callsignFieldInactive = new Field("div_callsign", false, false);

        Field depDestFieldMandatory = new Field("div_dep_dest", true, true);
        Field depDestFieldActive = new Field("div_dep_dest", false, true);
        Field depDestFieldInactive = new Field("div_dep_dest", false, false);

        Field dateFromUntilFieldMandatory = new Field("div_date_from_until", true, true);
        Field dateFromUntilFieldActive = new Field("div_date_from_until", false, true);
        Field dateFromUntilInactive = new Field("div_date_from_until", false, false);

        Field timeScheduleFieldMandatory = new Field("div_time_schedule", true, true);
        Field timeScheduleFieldActive = new Field("div_time_schedule", false, true);
        Field timeScheduleFieldInactive = new Field("div_time_schedule", false, false);

        Field durationFieldMandatory = new Field("div_duration", true, true);
        Field durationFieldActive = new Field("div_duration", false, true);
        Field durationFieldInactive = new Field("div_duration", false, false);

        Field exactLocationFieldMandatory = new Field("div_exact_location", true, true);
        Field exactLocationFieldActive = new Field("div_exact_location", false, true);
        Field exactLocationFieldInactive = new Field("div_exact_location", false, false);

        Field heightAltitudeFieldMandatory = new Field("div_height_altitude", true, true);
        Field heightAltitudeFieldActive = new Field("div_height_altitude", false, true);
        Field heightAltitudeFieldInactive = new Field("div_height_altitude", false, false);

        Field beamDirectionFieldMandatory = new Field("div_beam_direction", true, true);
        Field beamDirectionFieldActive = new Field("div_beam_direction", false, true);
        Field beamDirectionFieldInactive = new Field("div_beam_direction", false, false);

        Field payloadAttachedObjFieldMandatory = new Field("div_payload_attached_obj", true, true);
        Field payloadAttachedObjFieldActive = new Field("div_payload_attached_obj", false, true);
        Field payloadAttachedObjFieldInactive = new Field("div_payload_attached_obj", false, false);

        Field amountFieldMandatory = new Field("div_amount", true, true);
        Field amountFieldActive = new Field("div_amount", false, true);
        Field amountFieldInactive = new Field("div_amount", false, false);

        Field gpsCoordFieldMandatory = new Field("div_gps_coord", true, true);
        Field gpsCoordFieldActive = new Field("div_gps_coord", false, true);
        Field gpsCoordFieldInactive = new Field("div_gps_coord", false, false);

        Field radiusPlusFieldMandatory = new Field("div_radius_plus", true, true);
        Field radiusPlusFieldActive = new Field("div_radius_plus", false, true);
        Field radiusPlusFieldInactive = new Field("div_radius_plus", false, false);

        Field heightAltitudePlusFieldMandatory = new Field("div_height_altitude_plus", true, true);
        Field heightAltitudePlusFieldActive = new Field("div_height_altitude_plus", false, true);
        Field heightAltitudePlusFieldInactive = new Field("div_height_altitude_plus", false, false);

        Field selfDeclarationFieldMandatory = new Field("div_self_declaration", true, true);
        Field selfDeclarationFieldActive = new Field("div_self_declaration", false, true);
        Field selfDeclarationFieldInactive = new Field("div_self_declaration", false, false);

        ActivityType skyLanternActivityType = new ActivityType("Sky Lantern");
        AircraftType defaultAircraftType = new AircraftType("");
        skyLanternActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldInactive);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultAircraftType.getFieldList().add(amountFieldActive);
        listActivityType.add(skyLanternActivityType);

        ActivityType weatherBalloonActivityType = new ActivityType("Weather Balloon");
        defaultAircraftType.getFieldList().clear();
        weatherBalloonActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultAircraftType.getFieldList().add(amountFieldActive);
        listActivityType.add(weatherBalloonActivityType);

        ActivityType toyBalloonActivityType = new ActivityType("Toy Balloon");
        defaultAircraftType.getFieldList().clear();
        toyBalloonActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldInactive);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultAircraftType.getFieldList().add(amountFieldActive);
        listActivityType.add(toyBalloonActivityType);

        ActivityType captiveBalloonActivityType = new ActivityType("Captive Balloon");
        defaultAircraftType.getFieldList().clear();
        captiveBalloonActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldActive);
        listActivityType.add(captiveBalloonActivityType);

        ActivityType hotAirBalloonActivityType = new ActivityType("Hot Air Balloon");
        defaultAircraftType.getFieldList().clear();
        hotAirBalloonActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldMandatory);
        defaultAircraftType.getFieldList().add(depDestFieldMandatory);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldActive);
        listActivityType.add(hotAirBalloonActivityType);

        ActivityType gasBalloonActivityType = new ActivityType("Gas Balloon");
        defaultAircraftType.getFieldList().clear();
        gasBalloonActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldMandatory);
        defaultAircraftType.getFieldList().add(depDestFieldMandatory);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldActive);
        listActivityType.add(gasBalloonActivityType);

        ActivityType skyLightLaserActivityType = new ActivityType("Sky Light / Lase");
        defaultAircraftType.getFieldList().clear();
        skyLightLaserActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldInactive);
        defaultAircraftType.getFieldList().add(beamDirectionFieldActive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldInactive);
        listActivityType.add(skyLightLaserActivityType);

        ActivityType kiteActivityType = new ActivityType("Kite");
        defaultAircraftType.getFieldList().clear();
        kiteActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldInactive);
        listActivityType.add(kiteActivityType);

        ActivityType fireworkActivityType = new ActivityType("Firework");
        defaultAircraftType.getFieldList().clear();
        fireworkActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldInactive);
        listActivityType.add(fireworkActivityType);

        ActivityType flightWithHangGliderActivityType = new ActivityType("Flight With Hang Glider");
        defaultAircraftType.getFieldList().clear();
        flightWithHangGliderActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldInactive);
        listActivityType.add(flightWithHangGliderActivityType);

        AircraftType fixedWingAircraftType = new AircraftType("Fixed Wing Aircraft");
        AircraftType rotaryWingAircraftType = new AircraftType("Rotary Wing Aircraft");
        AircraftType rpasAircraftType = new AircraftType("PRAS");

        ActivityType photoFlightActivityType = new ActivityType("Photo Flight");
        photoFlightActivityType.getAircraftTypeList().add(fixedWingAircraftType);
        fixedWingAircraftType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftType.getFieldList().add(radiusPlusFieldInactive);
        fixedWingAircraftType.getFieldList().add(heightAltitudePlusFieldActive);
        fixedWingAircraftType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(photoFlightActivityType);

        photoFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftType);
        rotaryWingAircraftType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftType.getFieldList().add(radiusPlusFieldInactive);
        rotaryWingAircraftType.getFieldList().add(heightAltitudePlusFieldActive);
        rotaryWingAircraftType.getFieldList().add(selfDeclarationFieldInactive);
        photoFlightActivityType.getAircraftTypeList().add(fixedWingAircraftType);
        listActivityType.add(photoFlightActivityType);


        photoFlightActivityType.getAircraftTypeList().add(rpasAircraftType);
        rpasAircraftType.getFieldList().add(callsignFieldInactive);
        rpasAircraftType.getFieldList().add(depDestFieldInactive);
        rpasAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftType.getFieldList().add(durationFieldMandatory);
        rpasAircraftType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftType.getFieldList().add(gpsCoordFieldInactive);
        rpasAircraftType.getFieldList().add(radiusPlusFieldInactive);
        rpasAircraftType.getFieldList().add(heightAltitudePlusFieldActive);
        rpasAircraftType.getFieldList().add(selfDeclarationFieldActive);
        photoFlightActivityType.getAircraftTypeList().add(fixedWingAircraftType);
        listActivityType.add(photoFlightActivityType);

        ActivityType transportFlightActivityType = new ActivityType("Transport Flight");
        defaultAircraftType.getFieldList().clear();
        transportFlightActivityType.getAircraftTypeList().add(defaultAircraftType);
        defaultAircraftType.getFieldList().add(callsignFieldInactive);
        defaultAircraftType.getFieldList().add(depDestFieldInactive);
        defaultAircraftType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultAircraftType.getFieldList().add(timeScheduleFieldMandatory);
        defaultAircraftType.getFieldList().add(durationFieldMandatory);
        defaultAircraftType.getFieldList().add(exactLocationFieldMandatory);
        defaultAircraftType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultAircraftType.getFieldList().add(beamDirectionFieldInactive);
        defaultAircraftType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultAircraftType.getFieldList().add(amountFieldInactive);
        listActivityType.add(transportFlightActivityType);

        ActivityType taxiPassengerFlightActivityType = new ActivityType("Taxi Passenger Flight");
        listActivityType.add(taxiPassengerFlightActivityType);

        ActivityType parachuteActivityType = new ActivityType("Parachute");
        listActivityType.add(parachuteActivityType);

        ActivityType militaryMissionActivityType = new ActivityType("Military Mission");
        listActivityType.add(militaryMissionActivityType);

        ActivityType policeMissionActivityType = new ActivityType("Police Mission");
        listActivityType.add(policeMissionActivityType);

        ActivityType etcActivityType = new ActivityType("etc.");
        listActivityType.add(etcActivityType);
    }
}
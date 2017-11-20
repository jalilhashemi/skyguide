package ch.fhnw.skyguide;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ActivityTypeService {

    private List<ActivityType> list = new ArrayList<>();

    public ActivityType getAircraftTypeByName(String name) {
        Predicate<ActivityType> aircraftTypePredicate = a -> a.getName().equals(name);
        ActivityType obj = list.stream().filter(aircraftTypePredicate).findFirst().get();
        return obj;
    }

    public List<ActivityType> getAllAircraftTypes() {
        return list;
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

        ActivityType skyLaternActivityType = new ActivityType("Sky Lantern");
        skyLaternActivityType.getFieldList().add(callsignFieldInactive);
        skyLaternActivityType.getFieldList().add(depDestFieldInactive);
        skyLaternActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        skyLaternActivityType.getFieldList().add(timeScheduleFieldMandatory);
        skyLaternActivityType.getFieldList().add(durationFieldMandatory);
        skyLaternActivityType.getFieldList().add(exactLocationFieldMandatory);
        skyLaternActivityType.getFieldList().add(heightAltitudeFieldInactive);
        skyLaternActivityType.getFieldList().add(beamDirectionFieldInactive);
        skyLaternActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        skyLaternActivityType.getFieldList().add(amountFieldActive);
        list.add(skyLaternActivityType);

        ActivityType weatherBalloonActivityType = new ActivityType("Weather Balloon");
        weatherBalloonActivityType.getFieldList().add(callsignFieldInactive);
        weatherBalloonActivityType.getFieldList().add(depDestFieldInactive);
        weatherBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        weatherBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        weatherBalloonActivityType.getFieldList().add(durationFieldMandatory);
        weatherBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        weatherBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        weatherBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        weatherBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        weatherBalloonActivityType.getFieldList().add(amountFieldActive);
        list.add(weatherBalloonActivityType);

        ActivityType toyBalloonActivityType = new ActivityType("Toy Balloon");
        toyBalloonActivityType.getFieldList().add(callsignFieldInactive);
        toyBalloonActivityType.getFieldList().add(depDestFieldInactive);
        toyBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        toyBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        toyBalloonActivityType.getFieldList().add(durationFieldMandatory);
        toyBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        toyBalloonActivityType.getFieldList().add(heightAltitudeFieldInactive);
        toyBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        toyBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        toyBalloonActivityType.getFieldList().add(amountFieldActive);
        list.add(toyBalloonActivityType);

        ActivityType captiveBalloonActivityType = new ActivityType("Captive Balloon");
        captiveBalloonActivityType.getFieldList().add(callsignFieldInactive);
        captiveBalloonActivityType.getFieldList().add(depDestFieldInactive);
        captiveBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        captiveBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        captiveBalloonActivityType.getFieldList().add(durationFieldMandatory);
        captiveBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        captiveBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        captiveBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        captiveBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        captiveBalloonActivityType.getFieldList().add(amountFieldActive);
        list.add(captiveBalloonActivityType);

        ActivityType hotAirBalloonActivityType = new ActivityType("Hot Air Balloon");
        hotAirBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(depDestFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(durationFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        hotAirBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        hotAirBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        hotAirBalloonActivityType.getFieldList().add(amountFieldActive);
        list.add(hotAirBalloonActivityType);

        ActivityType gasBalloonActivityType = new ActivityType("Gas Balloon");
        gasBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        gasBalloonActivityType.getFieldList().add(depDestFieldMandatory);
        gasBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        gasBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        gasBalloonActivityType.getFieldList().add(durationFieldMandatory);
        gasBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        gasBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        gasBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        gasBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        gasBalloonActivityType.getFieldList().add(amountFieldActive);
        list.add(gasBalloonActivityType);

        ActivityType skyLightLaseActivityType = new ActivityType("Sky Light / Lase");
        skyLightLaseActivityType.getFieldList().add(callsignFieldInactive);
        skyLightLaseActivityType.getFieldList().add(depDestFieldInactive);
        skyLightLaseActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        skyLightLaseActivityType.getFieldList().add(timeScheduleFieldMandatory);
        skyLightLaseActivityType.getFieldList().add(durationFieldMandatory);
        skyLightLaseActivityType.getFieldList().add(exactLocationFieldMandatory);
        skyLightLaseActivityType.getFieldList().add(heightAltitudeFieldInactive);
        skyLightLaseActivityType.getFieldList().add(beamDirectionFieldActive);
        skyLightLaseActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        skyLightLaseActivityType.getFieldList().add(amountFieldInactive);
        list.add(skyLightLaseActivityType);

        ActivityType kiteActivityType = new ActivityType("Kite");
        kiteActivityType.getFieldList().add(callsignFieldInactive);
        kiteActivityType.getFieldList().add(depDestFieldInactive);
        kiteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        kiteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        kiteActivityType.getFieldList().add(durationFieldMandatory);
        kiteActivityType.getFieldList().add(exactLocationFieldMandatory);
        kiteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        kiteActivityType.getFieldList().add(beamDirectionFieldInactive);
        kiteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        kiteActivityType.getFieldList().add(amountFieldInactive);
        list.add(kiteActivityType);

        ActivityType fireworkActivityType = new ActivityType("Firework");
        fireworkActivityType.getFieldList().add(callsignFieldInactive);
        fireworkActivityType.getFieldList().add(depDestFieldInactive);
        fireworkActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fireworkActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fireworkActivityType.getFieldList().add(durationFieldMandatory);
        fireworkActivityType.getFieldList().add(exactLocationFieldMandatory);
        fireworkActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fireworkActivityType.getFieldList().add(beamDirectionFieldInactive);
        fireworkActivityType.getFieldList().add(payloadAttachedObjFieldInactive);;
        fireworkActivityType.getFieldList().add(amountFieldInactive);
        list.add(fireworkActivityType);

        ActivityType flightWithHangGliderActivityType = new ActivityType("Flight With Hang Glider");
        flightWithHangGliderActivityType.getFieldList().add(callsignFieldInactive);
        flightWithHangGliderActivityType.getFieldList().add(depDestFieldInactive);
        flightWithHangGliderActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        flightWithHangGliderActivityType.getFieldList().add(timeScheduleFieldMandatory);
        flightWithHangGliderActivityType.getFieldList().add(durationFieldMandatory);
        flightWithHangGliderActivityType.getFieldList().add(exactLocationFieldMandatory);
        flightWithHangGliderActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        flightWithHangGliderActivityType.getFieldList().add(beamDirectionFieldInactive);
        flightWithHangGliderActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        flightWithHangGliderActivityType.getFieldList().add(amountFieldInactive);
        list.add(flightWithHangGliderActivityType);

        ActivityType photoFlightActivityType = new ActivityType("Photo Flight");
        list.add(photoFlightActivityType);

        ActivityType transportFlightActivityType = new ActivityType("Transport Flight");
        list.add(transportFlightActivityType);

        ActivityType taxiPassengerFlightActivityType = new ActivityType("Taxi Passenger Flight");
        list.add(taxiPassengerFlightActivityType);

        ActivityType parachuteActivityType = new ActivityType("Parachute");
        list.add(parachuteActivityType);

        ActivityType militaryMissionActivityType = new ActivityType("Military Mission");
        list.add(militaryMissionActivityType);

        ActivityType policeMissionActivityType = new ActivityType("Police Mission");
        list.add(policeMissionActivityType);

        ActivityType etcActivityType = new ActivityType("etc.");
        list.add(etcActivityType);
    }
}
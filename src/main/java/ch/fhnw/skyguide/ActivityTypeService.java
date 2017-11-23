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
        Field dateFromUntilFieldInactive = new Field("div_date_from_until", false, false);

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

        Field radiusFieldMandatory = new Field("div_radius", true, true);
        Field radiusFieldActive = new Field("div_radius", false, true);
        Field radiusFieldInactive = new Field("div_radius", false, false);

        Field selfDeclarationFieldMandatory = new Field("div_self_declaration", true, true);
        Field selfDeclarationFieldActive = new Field("div_self_declaration", false, true);
        Field selfDeclarationFieldInactive = new Field("div_self_declaration", false, false);

        ActivityType skyLanternActivityType = new ActivityType("Sky Lantern");
        AircraftType defaultSkyLanternActivityType = new AircraftType("");
        skyLanternActivityType.getAircraftTypeList().add(defaultSkyLanternActivityType);
        defaultSkyLanternActivityType.getFieldList().add(callsignFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(depDestFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(durationFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(heightAltitudeFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultSkyLanternActivityType.getFieldList().add(amountFieldActive);
        defaultSkyLanternActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(radiusFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(skyLanternActivityType);

        ActivityType weatherBalloonActivityType = new ActivityType("Weather Balloon");
        AircraftType defaultWeatherBalloonActivityType = new AircraftType("");
        weatherBalloonActivityType.getAircraftTypeList().add(defaultWeatherBalloonActivityType);
        defaultWeatherBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(depDestFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultWeatherBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultWeatherBalloonActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(weatherBalloonActivityType);

        ActivityType toyBalloonActivityType = new ActivityType("Toy Balloon");
        AircraftType defaultToyBalloonActivityType = new AircraftType("");
        toyBalloonActivityType.getAircraftTypeList().add(defaultToyBalloonActivityType);
        defaultToyBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(depDestFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(heightAltitudeFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(toyBalloonActivityType);

        ActivityType captiveBalloonActivityType = new ActivityType("Captive Balloon");
        AircraftType  defaultCaptiveBalloonActivityType= new AircraftType("");
        captiveBalloonActivityType.getAircraftTypeList().add(defaultCaptiveBalloonActivityType);
        defaultCaptiveBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(depDestFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultCaptiveBalloonActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(captiveBalloonActivityType);

        ActivityType hotAirBalloonActivityType = new ActivityType("Hot Air Balloon");
        AircraftType defaultHotAirBalloonActivityType = new AircraftType("");
        hotAirBalloonActivityType.getAircraftTypeList().add(defaultHotAirBalloonActivityType);
        defaultHotAirBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(depDestFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultHotAirBalloonActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(hotAirBalloonActivityType);

        ActivityType gasBalloonActivityType = new ActivityType("Gas Balloon");
        AircraftType defaultGasBalloonActivityType = new AircraftType("");
        gasBalloonActivityType.getAircraftTypeList().add(defaultGasBalloonActivityType);
        defaultGasBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(depDestFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultGasBalloonActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(gasBalloonActivityType);

        ActivityType skyLightLaserActivityType = new ActivityType("Sky Light / Lase");
        AircraftType defaultSkyLightLaserActivityType = new AircraftType("");
        skyLightLaserActivityType.getAircraftTypeList().add(defaultSkyLightLaserActivityType);
        defaultSkyLightLaserActivityType.getFieldList().add(callsignFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(depDestFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(durationFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(heightAltitudeFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(beamDirectionFieldActive);
        defaultSkyLightLaserActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(amountFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(radiusFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(skyLightLaserActivityType);

        ActivityType kiteActivityType = new ActivityType("Kite");
        AircraftType defaultKiteActivityType  = new AircraftType("");
        kiteActivityType.getAircraftTypeList().add(defaultKiteActivityType);
        defaultKiteActivityType.getFieldList().add(callsignFieldInactive);
        defaultKiteActivityType.getFieldList().add(depDestFieldInactive);
        defaultKiteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultKiteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultKiteActivityType.getFieldList().add(durationFieldMandatory);
        defaultKiteActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultKiteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultKiteActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultKiteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultKiteActivityType.getFieldList().add(amountFieldInactive);
        defaultKiteActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultKiteActivityType.getFieldList().add(radiusFieldInactive);
        defaultKiteActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(kiteActivityType);

        ActivityType fireworkActivityType = new ActivityType("Firework");
        AircraftType defaultFireworkActivityType = new AircraftType("");
        fireworkActivityType.getAircraftTypeList().add(defaultFireworkActivityType);
        defaultFireworkActivityType.getFieldList().add(callsignFieldInactive);
        defaultFireworkActivityType.getFieldList().add(depDestFieldInactive);
        defaultFireworkActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(durationFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultFireworkActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultFireworkActivityType.getFieldList().add(amountFieldInactive);
        defaultFireworkActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultFireworkActivityType.getFieldList().add(radiusFieldInactive);
        defaultFireworkActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(fireworkActivityType);

        ActivityType flightWithHangGliderActivityType = new ActivityType("Flight With Hang Glider");
        AircraftType defaultfLightWithHangGliderActivityType = new AircraftType("");
        flightWithHangGliderActivityType.getAircraftTypeList().add(defaultfLightWithHangGliderActivityType);
        defaultfLightWithHangGliderActivityType.getFieldList().add(callsignFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(depDestFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(durationFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(amountFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(radiusFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(flightWithHangGliderActivityType);

        ActivityType transportFlightActivityType = new ActivityType("Transport Flight");
        AircraftType defaultTransportFlightActivityType = new AircraftType("");
        transportFlightActivityType.getAircraftTypeList().add(defaultTransportFlightActivityType);
        defaultTransportFlightActivityType.getFieldList().add(callsignFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(depDestFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(durationFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultTransportFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultTransportFlightActivityType.getFieldList().add(amountFieldInactive);
        defaultTransportFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultTransportFlightActivityType.getFieldList().add(radiusFieldInactive);
        defaultTransportFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(transportFlightActivityType);

        ActivityType photoFlightActivityType = new ActivityType("Photo Flight");
        AircraftType rotaryWingAircraftTypePhotoFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        photoFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypePhotoFlightActivityType);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypePhotoFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        photoFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypePhotoFlightActivityType);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypePhotoFlightActivityType = new AircraftType("PRAS");
        photoFlightActivityType.getAircraftTypeList().add(rpasAircraftTypePhotoFlightActivityType);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(depDestFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldActive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(photoFlightActivityType);

        ActivityType taxiPassengerFlightActivityType = new ActivityType("Taxi Passenger Flight");
        AircraftType rotaryWingAircraftTypeTaxiPassengerFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        taxiPassengerFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeTaxiPassengerFlightActivityType);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeTaxiPassengerFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        taxiPassengerFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTaxiPassengerFlightActivityType);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(taxiPassengerFlightActivityType);

        ActivityType parachuteActivityType = new ActivityType("Parachute");
        AircraftType rotaryWingAircraftTypeParachuteActivityType = new AircraftType("Rotary Wing Aircraft");
        parachuteActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeParachuteActivityType);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(radiusFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeParachuteActivityType = new AircraftType("Fixed Wing Aircraft");
        parachuteActivityType.getAircraftTypeList().add(fixedWingAircraftTypeParachuteActivityType);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(gpsCoordFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(radiusFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(parachuteActivityType);

        ActivityType militaryMissionActivityType = new ActivityType("Military Mission");
        AircraftType rotaryWingAircraftTypeMilitaryMissionActivityType = new AircraftType("Rotary Wing Aircraft");
        militaryMissionActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeMilitaryMissionActivityType);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeMilitaryMissionActivityType = new AircraftType("Fixed Wing Aircraft");
        militaryMissionActivityType.getAircraftTypeList().add(fixedWingAircraftTypeMilitaryMissionActivityType);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypeMilitaryMissionActivityType = new AircraftType("PRAS (e.g. ADS95)");
        militaryMissionActivityType.getAircraftTypeList().add(rpasAircraftTypeMilitaryMissionActivityType);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(depDestFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldActive);
        listActivityType.add(militaryMissionActivityType);

        ActivityType policeMissionActivityType = new ActivityType("Police Mission");
        AircraftType rotaryWingAircraftTypePoliceMissionActivityType = new AircraftType("Rotary Wing Aircraft");
        policeMissionActivityType.getAircraftTypeList().add(rotaryWingAircraftTypePoliceMissionActivityType);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypePoliceMissionActivityType = new AircraftType("Fixed Wing Aircraft");
        policeMissionActivityType.getAircraftTypeList().add(fixedWingAircraftTypePoliceMissionActivityType);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypePoliceMissionActivityType = new AircraftType("PRAS");
        policeMissionActivityType.getAircraftTypeList().add(rpasAircraftTypePoliceMissionActivityType);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(depDestFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldActive);
        listActivityType.add(policeMissionActivityType);
    }
}
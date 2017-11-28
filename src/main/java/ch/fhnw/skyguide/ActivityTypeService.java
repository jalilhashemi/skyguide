package ch.fhnw.skyguide;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ActivityTypeService {

    private List<ActivityType> listActivityType = new ArrayList<>();

    private final String FIELD_HEIGHT_ALTITUDE_ID = "field_height_altitude";
    private final String FIELD_HEIGHT_ALTITUDE_NAME = "Height / Altitude";

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
        Field callsignFieldMandatory = new Field("field_callsign", "Callsign", true, true);
        Field callsignFieldActive = new Field("field_callsign", "Callsign", false, true);
        Field callsignFieldInactive = new Field("field_callsign", "Callsign", false, false);

        Field depDestFieldMandatory = new Field("field_dep_dest", "DEP / DEST", true, true);
        Field depDestFieldActive = new Field("field_dep_dest", "DEP / DEST", false, true);
        Field depDestFieldInactive = new Field("field_dep_dest", "DEP / DEST", false, false);

        Field dateFromFieldMandatory = new Field("field_date_from", "Date from", true, true);
        Field dateFromFieldActive = new Field("field_date_from", "Date from", false, true);
        Field dateFromFieldInactive = new Field("field_date_from", "Date from", false, false);

        Field dateUntilFieldMandatory = new Field("field_date_until", "Date until", true, true);
        Field dateUntilFieldActive = new Field("field_date_until", "Date until", false, true);
        Field dateUntilFieldInactive = new Field("field_date_until", "Date until", false, false);

        Field timeScheduleFieldMandatory = new Field("field_time_schedule", "Time schedule", true, true);
        Field timeScheduleFieldActive = new Field("field_time_schedule", "Time schedule", false, true);
        Field timeScheduleFieldInactive = new Field("field_time_schedule", "Time schedule", false, false);

        Field durationFieldMandatory = new Field("field_duration", "Duration", true, true);
        Field durationFieldActive = new Field("field_duration", "Duration", false, true);
        Field durationFieldInactive = new Field("field_duration", "Duration", false, false);

        Field exactLocationFieldMandatory = new Field("field_location", "Location", true, true);
        Field exactLocationFieldActive = new Field("field_location", "Location", false, true);
        Field exactLocationFieldInactive = new Field("field_location", "Location", false, false);

      /*  Field heightAltitudeFieldMandatory = new Field(FIELD_HEIGHT_ALTITUDE_ID,FIELD_HEIGHT_ALTITUDE_NAME, true, true);
        Field heightAltitudeFieldActive = new Field(FIELD_HEIGHT_ALTITUDE_ID,FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        Field heightAltitudeFieldInactive = new Field(FIELD_HEIGHT_ALTITUDE_ID,FIELD_HEIGHT_ALTITUDE_NAME, false, false);
        */

        Field heightAltitudeFlRadioMandatory = new Field("radio_height_fl", "FL", true, true);
        Field heightAltitudeFlRadioActive = new Field("radio_height_fl", "FL", false, true);
        heightAltitudeFlRadioActive.setTooltip("This is the FL Tooltip.");
        Field heightAltitudeFlRadioInactive = new Field("radio_height_fl", "FL", false, false);

        Field heightAltitudeFtAmslRadiodMandatory = new Field("radio_height_ft_amsl", "ft AMSL", true, true);
        Field heightAltitudeFtAmslRadioActive = new Field("radio_height_ft_amsl", "ft AMSL", false, true);
        heightAltitudeFtAmslRadioActive.setTooltip("This is the ft AMSL Tooltip.");
        Field heightAltitudeFtAmslRadioInactive = new Field("radio_height_ft_amsl", "ft AMSL", false, false);

        Field heightAltitudeFtGndRadioMandatory = new Field("radio_height_ft_gnd", "ft GND", true, true);
        Field heightAltitudeFtGndRadioActive = new Field("radio_height_ft_gnd", "ft GND", false, true);
        heightAltitudeFtGndRadioActive.setTooltip("This is the ft GND Tooltip.");
        Field heightAltitudeFtGndRadioInactive = new Field("radio_height_ft_gnd", "ft GND", false, false);

        Field heightAltitudeMGndRadioMandatory = new Field("radio_height_m_gnd", "m GND", true, true);
        Field heightAltitudeMGndRadioActive = new Field("radio_height_m_gnd", "m GND", false, true);
        heightAltitudeMGndRadioActive.setTooltip("This is the m GND Tooltip.");
        Field heightAltitudeMGndRadioInactive = new Field("radio_height_m_gnd", "m GND", false, false);

        Field beamDirectionFieldMandatory = new Field("field_beam_direction", "Beam direction", true, true);
        Field beamDirectionFieldActive = new Field("field_beam_direction", "Beam direction", false, true);
        Field beamDirectionFieldInactive = new Field("field_beam_direction", "Beam direction", false, false);

        Field payloadAttachedObjFieldMandatory = new Field("field_payload_attached_obj", "Payload / Attached object", true, true);
        Field payloadAttachedObjFieldActive = new Field("field_payload_attached_obj", "Payload / Attached object", false, true);
        Field payloadAttachedObjFieldInactive = new Field("field_payload_attached_obj", "Payload / Attached object", false, false);

        Field amountFieldMandatory = new Field("field_amount", "Amount", true, true);
        Field amountFieldActive = new Field("field_amount", "Amount", false, true);
        Field amountFieldInactive = new Field("field_amount", "Amount", false, false);

        Field gpsCoordFieldMandatory = new Field("field_gps_coord", "Coordinate", true, true);
        Field gpsCoordFieldActive = new Field("field_gps_coord", "Coordinate", false, true);
        Field gpsCoordFieldInactive = new Field("field_gps_coord", "Coordinate", false, false);

        Field radiusFieldMandatory = new Field("field_radius", "Radius", true, true);
        Field radiusFieldActive = new Field("field_radius", "Radius", false, true);
        Field radiusFieldInactive = new Field("field_radius", "Radius", false, false);

        Field selfDeclarationFieldMandatory = new Field("textfield_self_declaration", "Self declaration", true, true);
        Field selfDeclarationFieldActive = new Field("textfield_self_declaration", "Self declaration", false, true);
        Field selfDeclarationFieldInactive = new Field("textfield_self_declaration", "Self declaration", false, false);

        ActivityType skyLanternActivityType = new ActivityType("Sky Lantern");
        AircraftType defaultSkyLanternActivityType = new AircraftType("");
        skyLanternActivityType.getAircraftTypeList().add(defaultSkyLanternActivityType);

        Field defaultSkyLanternHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, false);
        defaultSkyLanternHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        defaultSkyLanternHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        defaultSkyLanternHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        defaultSkyLanternHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultSkyLanternActivityType.getFieldList().add(callsignFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(depDestFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(durationFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(defaultSkyLanternHeightAltitudeField);
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

        Field defaultWeatherBalloonHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        defaultWeatherBalloonHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        defaultWeatherBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        defaultWeatherBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        defaultWeatherBalloonHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultWeatherBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(depDestFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(defaultWeatherBalloonHeightAltitudeField);
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

        Field defaultToyBalloonHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, false);
        defaultToyBalloonHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        defaultToyBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        defaultToyBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        defaultToyBalloonHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultToyBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(depDestFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(defaultToyBalloonHeightAltitudeField);
        defaultToyBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(toyBalloonActivityType);

        ActivityType captiveBalloonActivityType = new ActivityType("Captive Balloon");
        AircraftType defaultCaptiveBalloonActivityType = new AircraftType("");
        captiveBalloonActivityType.getAircraftTypeList().add(defaultCaptiveBalloonActivityType);

        Field defaultCaptiveBalloonHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        defaultCaptiveBalloonHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        defaultCaptiveBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        defaultCaptiveBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        defaultCaptiveBalloonHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        defaultCaptiveBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(depDestFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(defaultCaptiveBalloonHeightAltitudeField);
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

        Field defaultHotAirBalloonHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        defaultHotAirBalloonHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        defaultHotAirBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        defaultHotAirBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        defaultHotAirBalloonHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultHotAirBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(depDestFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(defaultHotAirBalloonHeightAltitudeField);
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

        Field gasBalloonHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        gasBalloonHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        gasBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        gasBalloonHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        gasBalloonHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultGasBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(depDestFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(gasBalloonHeightAltitudeField);
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

        Field skyLightLaserHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, false);
        skyLightLaserHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        skyLightLaserHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        skyLightLaserHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        skyLightLaserHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultSkyLightLaserActivityType.getFieldList().add(callsignFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(depDestFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(durationFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(skyLightLaserHeightAltitudeField);
        defaultSkyLightLaserActivityType.getFieldList().add(beamDirectionFieldActive);
        defaultSkyLightLaserActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(amountFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(gpsCoordFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(radiusFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(skyLightLaserActivityType);

        ActivityType kiteActivityType = new ActivityType("Kite");
        AircraftType defaultKiteActivityType = new AircraftType("");
        kiteActivityType.getAircraftTypeList().add(defaultKiteActivityType);

        Field kiteHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        kiteHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        kiteHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        kiteHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        kiteHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        defaultKiteActivityType.getFieldList().add(callsignFieldInactive);
        defaultKiteActivityType.getFieldList().add(depDestFieldInactive);
        defaultKiteActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultKiteActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultKiteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultKiteActivityType.getFieldList().add(durationFieldMandatory);
        defaultKiteActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultKiteActivityType.getFieldList().add(kiteHeightAltitudeField);
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

        Field fireworkHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fireworkHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        fireworkHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        fireworkHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fireworkHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        defaultFireworkActivityType.getFieldList().add(callsignFieldInactive);
        defaultFireworkActivityType.getFieldList().add(depDestFieldInactive);
        defaultFireworkActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(durationFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(fireworkHeightAltitudeField);
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

        Field flightWithHangGliderHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        flightWithHangGliderHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        flightWithHangGliderHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        flightWithHangGliderHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        flightWithHangGliderHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultfLightWithHangGliderActivityType.getFieldList().add(callsignFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(depDestFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(durationFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(flightWithHangGliderHeightAltitudeField);
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

        Field transportFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        transportFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        transportFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        transportFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        transportFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        defaultTransportFlightActivityType.getFieldList().add(callsignFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(depDestFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(durationFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        defaultTransportFlightActivityType.getFieldList().add(transportFlightHeightAltitudeField);
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

        Field rotaryWingAircraftTypePhotoFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        rotaryWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(rotaryWingAircraftTypePhotoFlightHeightAltitudeField);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypePhotoFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        photoFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypePhotoFlightActivityType);

        Field fixedWingAircraftTypePhotoFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(fixedWingAircraftTypePhotoFlightHeightAltitudeField);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypePhotoFlightActivityType = new AircraftType("RPAS");
        photoFlightActivityType.getAircraftTypeList().add(rpasAircraftTypePhotoFlightActivityType);

        Field rpasAircraftTypePhotoFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rpasAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioInactive);
        rpasAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypePhotoFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(depDestFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(rpasAircraftTypePhotoFlightHeightAltitudeField);
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

        Field rotaryWingAircraftTypeTaxiPassengerFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(rotaryWingAircraftTypeTaxiPassengerFlightHeightAltitudeField);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeTaxiPassengerFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        taxiPassengerFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTaxiPassengerFlightActivityType);

        Field fixedWingAircraftTypeTaxiPassengerFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeTaxiPassengerFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(fixedWingAircraftTypeTaxiPassengerFlightHeightAltitudeField);
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

        Field rotaryWingAircraftTypeParachuteHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        rotaryWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(rotaryWingAircraftTypeParachuteHeightAltitudeField);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(radiusFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeParachuteActivityType = new AircraftType("Fixed Wing Aircraft");
        parachuteActivityType.getAircraftTypeList().add(fixedWingAircraftTypeParachuteActivityType);

        Field fixedWingAircraftTypeParachuteHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeParachuteHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(fixedWingAircraftTypeParachuteHeightAltitudeField);
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

        Field rotaryWingAircraftTypeMilitaryMissionHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(rotaryWingAircraftTypeMilitaryMissionHeightAltitudeField);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeMilitaryMissionActivityType = new AircraftType("Fixed Wing Aircraft");
        militaryMissionActivityType.getAircraftTypeList().add(fixedWingAircraftTypeMilitaryMissionActivityType);

        Field fixedWingAircraftTypeMilitaryMissionHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(fixedWingAircraftTypeMilitaryMissionHeightAltitudeField);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypeMilitaryMissionActivityType = new AircraftType("RPAS (e.g. ADS95)");
        militaryMissionActivityType.getAircraftTypeList().add(rpasAircraftTypeMilitaryMissionActivityType);

        Field rpasAircraftTypeMilitaryMissionHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rpasAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rpasAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypeMilitaryMissionHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(depDestFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateUntilFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(rpasAircraftTypeMilitaryMissionHeightAltitudeField);
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

        Field rotaryWingAircraftTypePoliceMissionHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(rotaryWingAircraftTypePoliceMissionHeightAltitudeField);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypePoliceMissionActivityType = new AircraftType("Fixed Wing Aircraft");
        policeMissionActivityType.getAircraftTypeList().add(fixedWingAircraftTypePoliceMissionActivityType);

        Field fixedWingAircraftTypePoliceMissionHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(fixedWingAircraftTypePoliceMissionHeightAltitudeField);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypePoliceMissionActivityType = new AircraftType("RPAS");
        policeMissionActivityType.getAircraftTypeList().add(rpasAircraftTypePoliceMissionActivityType);

        Field rpasAircraftTypePoliceMissionHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rpasAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rpasAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rpasAircraftTypePoliceMissionHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(depDestFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(dateUntilFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(rpasAircraftTypePoliceMissionHeightAltitudeField);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldActive);
        listActivityType.add(policeMissionActivityType);

        ActivityType surveyFlightActivityType = new ActivityType("Survey Flight");
        AircraftType rotaryWingAircraftTypeSurveyFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        surveyFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeSurveyFlightActivityType);

        Field rotaryWingAircraftTypeSurveyFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(rotaryWingAircraftTypeSurveyFlightHeightAltitudeField);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeSurveyFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        surveyFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeSurveyFlightActivityType);

        Field fixedWingAircraftTypeSurveyFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(fixedWingAircraftTypeSurveyFlightHeightAltitudeField);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypeSurveyFlightActivityType = new AircraftType("RPAS");
        surveyFlightActivityType.getAircraftTypeList().add(rpasAircraftTypeSurveyFlightActivityType);

        Field rpasAircraftTypeSurveyFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rpasAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rpasAircraftTypeSurveyFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(depDestFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(rpasAircraftTypeSurveyFlightHeightAltitudeField);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(selfDeclarationFieldActive);
        listActivityType.add(surveyFlightActivityType);

        ActivityType tvRelayFlightActivityType = new ActivityType("TV Relay Flight");
        AircraftType rotaryWingAircraftTypeTvRelayFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        tvRelayFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeSurveyFlightActivityType);

        Field rotaryWingAircraftTypeTvRelayFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(rotaryWingAircraftTypeTvRelayFlightHeightAltitudeField);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeTvRelayFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        tvRelayFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTvRelayFlightActivityType);

        Field fixedWingAircraftTypeTvRelayFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeTvRelayFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(fixedWingAircraftTypeTvRelayFlightHeightAltitudeField);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(tvRelayFlightActivityType);

        ActivityType testFlightActivityType = new ActivityType("Test Flight");
        AircraftType rotaryWingAircraftTypeTestFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        testFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeTestFlightActivityType);

        Field rotaryWingAircraftTypeTestFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(rotaryWingAircraftTypeTestFlightHeightAltitudeField);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeTestFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        testFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTestFlightActivityType);

        Field fixedWingAircraftTypeTestFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeTestFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(fixedWingAircraftTypeTestFlightHeightAltitudeField);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(testFlightActivityType);

        ActivityType calibrationFlightActivityType = new ActivityType("Calibration Flight");
        AircraftType rotaryWingAircraftTypeCalibrationFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        calibrationFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeCalibrationFlightActivityType);

        Field rotaryWingAircraftTypeCalibrationFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(rotaryWingAircraftTypeCalibrationFlightHeightAltitudeField);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeCalibrationFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        calibrationFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeCalibrationFlightActivityType);

        Field fixedWingAircraftTypeCalibrationFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(fixedWingAircraftTypeCalibrationFlightHeightAltitudeField);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypeCalibrationFlightActivityType = new AircraftType("RPAS");
        calibrationFlightActivityType.getAircraftTypeList().add(rpasAircraftTypeCalibrationFlightActivityType);

        Field rpasAircraftTypeCalibrationFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rpasAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rpasAircraftTypeCalibrationFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioActive);

        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(depDestFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(rpasAircraftTypeCalibrationFlightHeightAltitudeField);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(radiusFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(selfDeclarationFieldActive);
        listActivityType.add(calibrationFlightActivityType);

        ActivityType contestActivityType = new ActivityType("Contest");
        AircraftType rotaryWingAircraftTypeContestActivityType = new AircraftType("Rotary Wing Aircraft");
        contestActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeContestActivityType);

        Field rotaryWingAircraftTypeContestHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeContestActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(rotaryWingAircraftTypeContestHeightAltitudeField);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeContestActivityType = new AircraftType("Fixed Wing Aircraft");
        contestActivityType.getAircraftTypeList().add(fixedWingAircraftTypeContestActivityType);

        Field fixedWingAircraftTypeContestHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeContestActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(fixedWingAircraftTypeContestHeightAltitudeField);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType rpasAircraftTypeContestActivityType = new AircraftType("Glider");
        contestActivityType.getAircraftTypeList().add(rpasAircraftTypeContestActivityType);

        Field rpasAircraftTypeContestHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rpasAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        rpasAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypeContestHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rpasAircraftTypeContestActivityType.getFieldList().add(callsignFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(depDestFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(dateFromFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(dateUntilFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(exactLocationFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(rpasAircraftTypeContestHeightAltitudeField);
        rpasAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(radiusFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(contestActivityType);

        ActivityType airshowAerobaticFlightActivityType = new ActivityType("Airshow / Aerobatic Flight");
        AircraftType rotaryWingAircraftTypeAirshowAerobaticFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        airshowAerobaticFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeAirshowAerobaticFlightActivityType);

        Field rotaryWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        rotaryWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(depDestFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(rotaryWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        AircraftType fixedWingAircraftTypeAirshowAerobaticFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        airshowAerobaticFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeAirshowAerobaticFlightActivityType);

        Field fixedWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField = new Field(FIELD_HEIGHT_ALTITUDE_ID, FIELD_HEIGHT_ALTITUDE_NAME, false, true);
        fixedWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField.getOptions().add(heightAltitudeMGndRadioInactive);

        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(depDestFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(dateFromFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(dateUntilFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(timeScheduleFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(exactLocationFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(fixedWingAircraftTypeAirshowAerobaticFlightHeightAltitudeField);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(gpsCoordFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);
        listActivityType.add(airshowAerobaticFlightActivityType);
    }
}
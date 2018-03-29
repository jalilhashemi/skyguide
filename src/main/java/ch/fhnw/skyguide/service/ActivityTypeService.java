package ch.fhnw.skyguide.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ActivityTypeService {

    private List<ActivityType> listActivityType = new ArrayList<>();

    public ActivityType getActivityTypeByName(String name) {
        Predicate<ActivityType> activityTypePredicate = a -> a.getLabel().equals(name);
        ActivityType obj = listActivityType.stream().filter(activityTypePredicate).findFirst().get();
        return obj;
    }

    public List<ActivityType> getAllActivityTypes() {
        return listActivityType;
    }

    private List<AircraftType> listAircraftType = new ArrayList<>();

    public AircraftType getAircraftTypeByName(String name) {
        Predicate<AircraftType> aircraftTypePredicate = a -> a.getLabel().equals(name);
        AircraftType obj = listAircraftType.stream().filter(aircraftTypePredicate).findFirst().get();
        return obj;
    }

    public List<AircraftType> getAllAircraftTypes() {
        return listAircraftType;
    }

    {
        Field callsignFieldMandatory = new Field("field_callsign", "callsign","Callsign", "Callsign", true, true);
        Field callsignFieldActive = new Field("field_callsign", "callsign", "Callsign", "Callsign",false, true);
        Field callsignFieldInactive = new Field("field_callsign", "callsign", "Callsign", "Callsign",false, false);

        Field departureFieldMandatory = new Field("field_departure","departure", "Departure","From", true, true);
        Field departureFieldActive = new Field("field_departure","departure" ,"Departure", "From",false, true);
        Field departureFieldInactive = new Field("field_departure","departure", "Departure", "From",false, false);

        Field destinationFieldMandatory = new Field("field_destination","destination", "Destination", "To", true, true);
        Field destinationFieldActive = new Field("field_destination","destination", "Destination", "To", false, true);
        Field destinationFieldInactive = new Field("field_destination","destination", "Destination", "To", false, false);

        Field dateFromUntilFieldMandatory = new Field("field_date_from_until","dateFromUntil", "Date from / Until", "DD.MM.YYYY - DD.MM.YYYY",true, true);
        Field dateFromUntilFieldActive = new Field("field_date_from_until","dateFromUntil", "Date from / Until", "DD.MM.YYYY - DD.MM.YYYY",false, true);
        Field dateFromUntilFieldInactive = new Field("field_date_from_until","dateFromUntil", "Date from / Until", "DD.MM.YYYY - DD.MM.YYYY",false, false);

        Field timeScheduleFromFieldMandatory = new Field("field_time_schedule_from", "timeFrom", "Time schedule (from)", "HH:MM",true, true);
        Field timeScheduleFromFieldActive = new Field("field_time_schedule_from", "timeFrom", "Time schedule (from)", "HH:MM",false, true);
        Field timeScheduleFromFieldInactive = new Field("field_time_schedule_from", "timeFrom", "Time schedule (from)","HH:MM", false, false);

        Field timeScheduleUntilFieldMandatory = new Field("field_time_schedule_until", "timeUntil", "Time schedule (until)", "HH:MM",true, true);
        Field timeScheduleUntilieldActive = new Field("field_time_schedule_until", "timeUntil", "Time schedule (until)", "HH:MM",false, true);
        Field timeScheduleUntilFieldInactive = new Field("field_time_schedule_until", "timeUntil", "Time schedule (until)", "HH:MM",false, false);

        Field durationFieldMandatory = new Field("field_duration","duration", "Duration", "HH", true, true);
        Field durationFieldActive = new Field("field_duration","duration", "Duration", "HH", false, true);
        Field durationFieldInactive = new Field("field_duration","duration", "Duration", "HH", false, false);

        Field locationFieldMandatory = new Field("field_location","location", "Location", "Location",true, true);
        Field locationdeFieldActive = new Field("field_location","location", "Location", "Location",false, true);
        Field locationFieldInactive = new Field("field_location","location", "Location", "Location",false, false);

        Field heightAltitudeFieldMandatory = new Field("field_height_altitude","", "Height / Altitude", "",true, true);
        Field heightAltitudeFieldActive = new Field("field_height_altitude", "","Height / Altitude","",true, true);
        Field heightAltitudeFieldInactive = new Field("field_height_altitude","", "Height / Altitude", "",false, false);

        Field heightAltitudeFlRadioMandatory = new Field("radio_height_fl","", "FL", "",true, true);
        Field heightAltitudeFlRadioActive = new Field("radio_height_fl","", "FL", "",false, true);
        heightAltitudeFlRadioActive.setTooltip("This is the FL Tooltip.");
        Field heightAltitudeFlRadioInactive = new Field("radio_height_fl","", "FL", "",false, false);

        Field heightAltitudeFtAmslRadiodMandatory = new Field("radio_height_ft_amsl","", "ft AMSL", "",true, true);
        Field heightAltitudeFtAmslRadioActive = new Field("radio_height_ft_amsl","", "ft AMSL", "",false, true);
        heightAltitudeFtAmslRadioActive.setTooltip("This is the ft AMSL Tooltip.");
        Field heightAltitudeFtAmslRadioInactive = new Field("radio_height_ft_amsl","", "ft AMSL", "",false, false);

        Field heightAltitudeFtGndRadioMandatory = new Field("radio_height_ft_gnd", "","ft GND", "",true, true);
        Field heightAltitudeFtGndRadioActive = new Field("radio_height_ft_gnd", "","ft GND", "",false, true);
        heightAltitudeFtGndRadioActive.setTooltip("This is the ft GND Tooltip.");
        Field heightAltitudeFtGndRadioInactive = new Field("radio_height_ft_gnd", "","ft GND", "",false, false);

        Field heightAltitudeMGndRadioMandatory = new Field("radio_height_m_gnd", "","m GND", "",true, true);
        Field heightAltitudeMGndRadioActive = new Field("radio_height_m_gnd", "","m GND", "",false, true);
        heightAltitudeMGndRadioActive.setTooltip("This is the m GND Tooltip.");
        Field heightAltitudeMGndRadioInactive = new Field("radio_height_m_gnd","", "m GND", "",false, false);

        Field beamDirectionFieldMandatory = new Field("field_beam_direction","beamDirection", "Beam direction", "",true, true);
        Field beamDirectionFieldActive = new Field("field_beam_direction","beamDirection", "Beam direction", "",false, true);
        Field beamDirectionFieldInactive = new Field("field_beam_direction","beamDirection", "Beam direction", "",false, false);

        Field payloadAttachedObjFieldMandatory = new Field("field_payload_attached_obj","payloadAttachedObj", "Payload / Attached object", "",true, true);
        Field payloadAttachedObjFieldActive = new Field("field_payload_attached_obj","payloadAttachedObj", "Payload / Attached object", "",false, true);
        Field payloadAttachedObjFieldInactive = new Field("field_payload_attached_obj","payloadAttachedObj", "Payload / Attached object", "",false, false);

        Field amountFieldMandatory = new Field("field_amount","amount", "Amount", "",true, true);
        Field amountFieldActive = new Field("field_amount","amount", "Amount", "",false, true);
        Field amountFieldInactive = new Field("field_amount","amount", "Amount", "",false, false);

        Field gpsCoordFieldMandatory = new Field("field_gps_coord","coordinates", "Coordinate (latitude, longitude)", "47°09′43.999″ N 7°40′33.646″E",true, true);
        Field gpsCoordFieldActive = new Field("field_gps_coord","coordinates", "Coordinate (latitude, longitude)", "47°09′43.999″ N 7°40′33.646″E",false, true);
        Field gpsCoordFieldInactive = new Field("field_gps_coord","coordinates", "Coordinate (latitude, longitude)", "47°09′43.999″ N 7°40′33.646″E",false, false);

        Field radiusFieldMandatory = new Field("field_radius","radius", "Radius", "Meter",true, true);
        Field radiusFieldActive = new Field("field_radius","radius", "Radius", "Meter",false, true);
        Field radiusFieldInactive = new Field("field_radius","radius", "Radius", "Meter",false, false);

        Field selfDeclarationFieldMandatory = new Field("textfield_self_declaration","selfDeclaration", "Self declaration", "",true, true);
        Field selfDeclarationFieldActive = new Field("textfield_self_declaration","selfDeclaration", "Self declaration", "",false, true);
        Field selfDeclarationFieldInactive = new Field("textfield_self_declaration","selfDeclaration", "Self declaration", "",false, false);

        Field freeAnswer1FieldMandatory = new Field("free_answer_1","freeAnswer1", "Are the altitudes flexible or not?", "",true, true);
        Field freeAnswer1FieldActive = new Field("free_answer_1","freeAnswer1", "Are the altitudes flexible or not?", "",false, true);
        Field freeAnswer1FieldInactive = new Field("free_answer_1","freeAnswer1", "Are the altitudes flexible or not?", "",false, false);

        Field freeAnswer2FieldMandatory = new Field("free_answer_2","freeAnswer2", "Is it possible to interrupt the mission?", "",true, true);
        Field freeAnswer2FieldActive = new Field("free_answer_2","freeAnswer2", "Is it possible to interrupt the mission?", "",false, true);
        Field freeAnswer2FieldInactive = new Field("free_answer_2","freeAnswer2", "Is it possible to interrupt the mission?", "",false, false);

        Field freeAnswer3FieldMandatory = new Field("free_answer_3","freeAnswer3", "Is the mission dependent on a certain time frame, e.g. due to the position of the sun?", "",true, true);
        Field freeAnswer3FieldActive = new Field("free_answer_3","freeAnswer3", "Is the mission dependent on a certain time frame, e.g. due to the position of the sun?", "",false, true);
        Field freeAnswer3FieldInactive = new Field("free_answer_3","freeAnswer3", "Is the mission dependent on a certain time frame, e.g. due to the position of the sun?", "",false, false);

        Field freeAnswer4FieldMandatory = new Field("free_answer_4","freeAnswer4", "Is the mission dependent on certain conditions (no clouds, no snow, no leaves)?", "",true, true);
        Field freeAnswer4FieldActive = new Field("free_answer_4","freeAnswer4", "Is the mission dependent on certain conditions (no clouds, no snow, no leaves)?", "",false, true);
        Field freeAnswer4FieldInactive = new Field("free_answer_4","freeAnswer4", "Is the mission dependent on certain conditions (no clouds, no snow, no leaves)?", "",false, false);






        ActivityType skyLanternActivityType = new ActivityType("Sky Lantern");
        AircraftType defaultSkyLanternActivityType = new AircraftType("");
        skyLanternActivityType.getAircraftTypeList().add(defaultSkyLanternActivityType);
        defaultSkyLanternActivityType.getFieldList().add(callsignFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(departureFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(destinationFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(durationFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(locationFieldMandatory);
        defaultSkyLanternActivityType.getFieldList().add(heightAltitudeFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultSkyLanternActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        defaultSkyLanternActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultSkyLanternActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultSkyLanternActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultSkyLanternActivityType.getFieldList().add(amountFieldActive);
        defaultSkyLanternActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultSkyLanternActivityType.getFieldList().add(radiusFieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultSkyLanternActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultSkyLanternActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(skyLanternActivityType);

        ActivityType weatherBalloonActivityType = new ActivityType("Weather Balloon");
        AircraftType defaultWeatherBalloonActivityType = new AircraftType("");
        weatherBalloonActivityType.getAircraftTypeList().add(defaultWeatherBalloonActivityType);
        defaultWeatherBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(departureFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(destinationFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(locationFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultWeatherBalloonActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        defaultWeatherBalloonActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        defaultWeatherBalloonActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        defaultWeatherBalloonActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultWeatherBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultWeatherBalloonActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultWeatherBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultWeatherBalloonActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultWeatherBalloonActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(weatherBalloonActivityType);

        ActivityType toyBalloonActivityType = new ActivityType("Toy Balloon");
        AircraftType defaultToyBalloonActivityType = new AircraftType("");
        toyBalloonActivityType.getAircraftTypeList().add(defaultToyBalloonActivityType);
        defaultToyBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(departureFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(destinationFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(locationFieldMandatory);
        defaultToyBalloonActivityType.getFieldList().add(heightAltitudeFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultToyBalloonActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        defaultToyBalloonActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultToyBalloonActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultToyBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(payloadAttachedObjFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultToyBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultToyBalloonActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultToyBalloonActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(toyBalloonActivityType);

        ActivityType captiveBalloonActivityType = new ActivityType("Captive Balloon");
        AircraftType defaultCaptiveBalloonActivityType = new AircraftType("");
        captiveBalloonActivityType.getAircraftTypeList().add(defaultCaptiveBalloonActivityType);
        defaultCaptiveBalloonActivityType.getFieldList().add(callsignFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(departureFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(destinationFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(locationFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultCaptiveBalloonActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        defaultCaptiveBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultCaptiveBalloonActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultCaptiveBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultCaptiveBalloonActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultCaptiveBalloonActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(captiveBalloonActivityType);

        ActivityType hotAirBalloonActivityType = new ActivityType("Hot Air Balloon");
        AircraftType defaultHotAirBalloonActivityType = new AircraftType("");
        hotAirBalloonActivityType.getAircraftTypeList().add(defaultHotAirBalloonActivityType);
        defaultHotAirBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(departureFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(destinationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(locationFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultHotAirBalloonActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        defaultHotAirBalloonActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultHotAirBalloonActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultHotAirBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultHotAirBalloonActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultHotAirBalloonActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(hotAirBalloonActivityType);

        ActivityType gasBalloonActivityType = new ActivityType("Gas Balloon");
        AircraftType defaultGasBalloonActivityType = new AircraftType("");
        gasBalloonActivityType.getAircraftTypeList().add(defaultGasBalloonActivityType);
        defaultGasBalloonActivityType.getFieldList().add(callsignFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(departureFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(destinationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(durationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(locationFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultGasBalloonActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultGasBalloonActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        defaultGasBalloonActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultGasBalloonActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultGasBalloonActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(amountFieldActive);
        defaultGasBalloonActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultGasBalloonActivityType.getFieldList().add(radiusFieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultGasBalloonActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultGasBalloonActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(gasBalloonActivityType);

        ActivityType skyLightLaserActivityType = new ActivityType("Sky Light / Laser");
        AircraftType defaultSkyLightLaserActivityType = new AircraftType("");
        skyLightLaserActivityType.getAircraftTypeList().add(defaultSkyLightLaserActivityType);
        defaultSkyLightLaserActivityType.getFieldList().add(callsignFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(departureFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(destinationFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(durationFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(locationFieldMandatory);
        defaultSkyLightLaserActivityType.getFieldList().add(heightAltitudeFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(beamDirectionFieldActive);
        defaultSkyLightLaserActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(amountFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultSkyLightLaserActivityType.getFieldList().add(radiusFieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultSkyLightLaserActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultSkyLightLaserActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(skyLightLaserActivityType);

        ActivityType kiteActivityType = new ActivityType("Kite");
        AircraftType defaultKiteActivityType = new AircraftType("");
        kiteActivityType.getAircraftTypeList().add(defaultKiteActivityType);
        defaultKiteActivityType.getFieldList().add(callsignFieldInactive);
        defaultKiteActivityType.getFieldList().add(departureFieldInactive);
        defaultKiteActivityType.getFieldList().add(destinationFieldInactive);
        defaultKiteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultKiteActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultKiteActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultKiteActivityType.getFieldList().add(durationFieldMandatory);
        defaultKiteActivityType.getFieldList().add(locationFieldMandatory);
        defaultKiteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultKiteActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultKiteActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        defaultKiteActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultKiteActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        defaultKiteActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultKiteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultKiteActivityType.getFieldList().add(amountFieldInactive);
        defaultKiteActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultKiteActivityType.getFieldList().add(radiusFieldInactive);
        defaultKiteActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultKiteActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultKiteActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultKiteActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultKiteActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(kiteActivityType);

        ActivityType fireworkActivityType = new ActivityType("Firework");
        AircraftType defaultFireworkActivityType = new AircraftType("");
        fireworkActivityType.getAircraftTypeList().add(defaultFireworkActivityType);
        defaultFireworkActivityType.getFieldList().add(callsignFieldInactive);
        defaultFireworkActivityType.getFieldList().add(departureFieldInactive);
        defaultFireworkActivityType.getFieldList().add(destinationFieldInactive);
        defaultFireworkActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(durationFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(locationFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultFireworkActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultFireworkActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        defaultFireworkActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        defaultFireworkActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        defaultFireworkActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultFireworkActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultFireworkActivityType.getFieldList().add(amountFieldInactive);
        defaultFireworkActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultFireworkActivityType.getFieldList().add(radiusFieldInactive);
        defaultFireworkActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultFireworkActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultFireworkActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultFireworkActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultFireworkActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(fireworkActivityType);

        ActivityType flightWithHangGliderActivityType = new ActivityType("Flight With Hang Glider");
        AircraftType defaultfLightWithHangGliderActivityType = new AircraftType("");
        flightWithHangGliderActivityType.getAircraftTypeList().add(defaultfLightWithHangGliderActivityType);
        defaultfLightWithHangGliderActivityType.getFieldList().add(callsignFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(departureFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(destinationFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(durationFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(locationFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        defaultfLightWithHangGliderActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(beamDirectionFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(amountFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(gpsCoordFieldActive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(radiusFieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(selfDeclarationFieldInactive);

        defaultfLightWithHangGliderActivityType.getFieldList().add(freeAnswer1FieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(freeAnswer2FieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(freeAnswer3FieldInactive);
        defaultfLightWithHangGliderActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(flightWithHangGliderActivityType);

        ActivityType transportFlightActivityType = new ActivityType("Transport Flight");
        AircraftType rotaryWingAircraftTypeTransportFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        transportFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeTransportFlightActivityType);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypeTransportFlightActivityType = new AircraftType("RPAS");
        transportFlightActivityType.getAircraftTypeList().add(rpasAircraftTypeTransportFlightActivityType);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(departureFieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(destinationFieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypeTransportFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(transportFlightActivityType);

        ActivityType photoFlightActivityType = new ActivityType("Photo Flight");
        AircraftType fixedWingAircraftTypePhotoFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        photoFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypePhotoFlightActivityType);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer1FieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer2FieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer3FieldMandatory);
        fixedWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer4FieldMandatory);

        AircraftType rotaryWingAircraftTypePhotoFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        photoFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypePhotoFlightActivityType);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypePhotoFlightActivityType = new AircraftType("RPAS");
        photoFlightActivityType.getAircraftTypeList().add(rpasAircraftTypePhotoFlightActivityType);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(departureFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(destinationFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypePhotoFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(photoFlightActivityType);

        ActivityType taxiPassengerFlightActivityType = new ActivityType("Taxi Passenger Flight");
        AircraftType fixedWingAircraftTypeTaxiPassengerFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        taxiPassengerFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTaxiPassengerFlightActivityType);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeTaxiPassengerFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        taxiPassengerFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeTaxiPassengerFlightActivityType);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeTaxiPassengerFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        listActivityType.add(taxiPassengerFlightActivityType);

        ActivityType parachuteActivityType = new ActivityType("Parachute");
        AircraftType fixedWingAircraftTypeParachuteActivityType = new AircraftType("Fixed Wing Aircraft");
        parachuteActivityType.getAircraftTypeList().add(fixedWingAircraftTypeParachuteActivityType);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(gpsCoordFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(radiusFieldMandatory);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeParachuteActivityType = new AircraftType("Rotary Wing Aircraft");
        parachuteActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeParachuteActivityType);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(radiusFieldMandatory);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeParachuteActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(parachuteActivityType);

        ActivityType militaryMissionActivityType = new ActivityType("Military Mission");
        AircraftType fixedWingAircraftTypeMilitaryMissionActivityType = new AircraftType("Fixed Wing Aircraft");
        militaryMissionActivityType.getAircraftTypeList().add(fixedWingAircraftTypeMilitaryMissionActivityType);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeMilitaryMissionActivityType = new AircraftType("Rotary Wing Aircraft");
        militaryMissionActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeMilitaryMissionActivityType);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypeMilitaryMissionActivityType = new AircraftType("RPAS (e.g. ADS95)");
        militaryMissionActivityType.getAircraftTypeList().add(rpasAircraftTypeMilitaryMissionActivityType);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(departureFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(destinationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(gpsCoordFieldActive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(radiusFieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(selfDeclarationFieldActive);

        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypeMilitaryMissionActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(militaryMissionActivityType);

        ActivityType policeMissionActivityType = new ActivityType("Police Mission");
        AircraftType fixedWingAircraftTypePoliceMissionActivityType = new AircraftType("Fixed Wing Aircraft");
        policeMissionActivityType.getAircraftTypeList().add(fixedWingAircraftTypePoliceMissionActivityType);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypePoliceMissionActivityType = new AircraftType("Rotary Wing Aircraft");
        policeMissionActivityType.getAircraftTypeList().add(rotaryWingAircraftTypePoliceMissionActivityType);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypePoliceMissionActivityType = new AircraftType("RPAS");
        policeMissionActivityType.getAircraftTypeList().add(rpasAircraftTypePoliceMissionActivityType);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(departureFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(destinationFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(selfDeclarationFieldActive);

        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypePoliceMissionActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(policeMissionActivityType);

        ActivityType surveyFlightActivityType = new ActivityType("Survey Flight");
        AircraftType fixedWingAircraftTypeSurveyFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        surveyFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeSurveyFlightActivityType);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeSurveyFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        surveyFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeSurveyFlightActivityType);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypeSurveyFlightActivityType = new AircraftType("RPAS");
        surveyFlightActivityType.getAircraftTypeList().add(rpasAircraftTypeSurveyFlightActivityType);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(departureFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(destinationFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(selfDeclarationFieldActive);

        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypeSurveyFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(surveyFlightActivityType);

        ActivityType tvRelayFlightActivityType = new ActivityType("TV Relay Flight");
        AircraftType fixedWingAircraftTypeTvRelayFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        tvRelayFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTvRelayFlightActivityType);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeTvRelayFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        tvRelayFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeSurveyFlightActivityType);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeTvRelayFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(tvRelayFlightActivityType);

        ActivityType testFlightActivityType = new ActivityType("Test Flight");
        AircraftType fixedWingAircraftTypeTestFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        testFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeTestFlightActivityType);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeTestFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        testFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeTestFlightActivityType);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeTestFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(testFlightActivityType);

        ActivityType calibrationFlightActivityType = new ActivityType("Calibration Flight");
        AircraftType fixedWingAircraftTypeCalibrationFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        calibrationFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeCalibrationFlightActivityType);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeCalibrationFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        calibrationFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeCalibrationFlightActivityType);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypeCalibrationFlightActivityType = new AircraftType("RPAS");
        calibrationFlightActivityType.getAircraftTypeList().add(rpasAircraftTypeCalibrationFlightActivityType);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(departureFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(destinationFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(radiusFieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(selfDeclarationFieldActive);

        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypeCalibrationFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(calibrationFlightActivityType);

        ActivityType contestActivityType = new ActivityType("Contest");
        AircraftType fixedWingAircraftTypeContestActivityType = new AircraftType("Fixed Wing Aircraft");
        contestActivityType.getAircraftTypeList().add(fixedWingAircraftTypeContestActivityType);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType gliderAircraftTypeContestActivityType = new AircraftType("Glider");
        contestActivityType.getAircraftTypeList().add(gliderAircraftTypeContestActivityType);
        gliderAircraftTypeContestActivityType.getFieldList().add(callsignFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(departureFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(destinationFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(locationFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        gliderAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        gliderAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        gliderAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldActive);
        gliderAircraftTypeContestActivityType.getFieldList().add(radiusFieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);

        gliderAircraftTypeContestActivityType.getFieldList().add(freeAnswer1FieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(freeAnswer2FieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(freeAnswer3FieldInactive);
        gliderAircraftTypeContestActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeContestActivityType = new AircraftType("Rotary Wing Aircraft");
        contestActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeContestActivityType);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeContestActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rpasAircraftTypeContestActivityType = new AircraftType("RPAS");
        contestActivityType.getAircraftTypeList().add(rpasAircraftTypeContestActivityType);
        rpasAircraftTypeContestActivityType.getFieldList().add(callsignFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(departureFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(destinationFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(durationFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(locationFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtAmslRadioInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(heightAltitudeMGndRadioActive);
        rpasAircraftTypeContestActivityType.getFieldList().add(beamDirectionFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(amountFieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(gpsCoordFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(radiusFieldMandatory);
        rpasAircraftTypeContestActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rpasAircraftTypeContestActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rpasAircraftTypeContestActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(contestActivityType);

        ActivityType airshowAerobaticFlightActivityType = new ActivityType("Airshow");
        AircraftType fixedWingAircraftTypeAirshowAerobaticFlightActivityType = new AircraftType("Fixed Wing Aircraft");
        airshowAerobaticFlightActivityType.getAircraftTypeList().add(fixedWingAircraftTypeAirshowAerobaticFlightActivityType);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(callsignFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(departureFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(destinationFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(durationFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(locationFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFlRadioActive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(amountFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(radiusFieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        fixedWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);

        AircraftType rotaryWingAircraftTypeAirshowAerobaticFlightActivityType = new AircraftType("Rotary Wing Aircraft");
        airshowAerobaticFlightActivityType.getAircraftTypeList().add(rotaryWingAircraftTypeAirshowAerobaticFlightActivityType);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(callsignFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(departureFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(destinationFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(dateFromUntilFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(timeScheduleFromFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(timeScheduleUntilFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(durationFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(locationFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFieldMandatory);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFlRadioInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFtAmslRadioActive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeFtGndRadioActive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(heightAltitudeMGndRadioInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(beamDirectionFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(payloadAttachedObjFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(amountFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(gpsCoordFieldActive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(radiusFieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(selfDeclarationFieldInactive);

        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer1FieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer2FieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer3FieldInactive);
        rotaryWingAircraftTypeAirshowAerobaticFlightActivityType.getFieldList().add(freeAnswer4FieldInactive);
        listActivityType.add(airshowAerobaticFlightActivityType);

        listActivityType.sort((o1, o2)->o1.getLabel().compareTo(o2.getLabel()));
    }
}
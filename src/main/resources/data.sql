--drop database skyguide;
--create database skyguide;

--use skyguide;

INSERT INTO activity_type (name) VALUES
('Airshow'),
('Calibration Flight'),
('Captive'),
('contest'),
('firework'),
('flightWithHangGlider'),
('gasBalloon'),
('gotAirBalloon'),
('kite'),
('militaryMission'),
('parachute'),
('photoFlight'),
('policeMission'),
('skyLantern'),
('skyLightLase'),
('surveyFlight'),
('tVRelayFlight'),
('taxiPassengerFlight'),
('testFlight'),
('toyBalloon'),
('transportFlight'),
('weatherBalloon');

INSERT INTO height_type (name) VALUES
('ft AMSL'),
('ft GND'),
('m GND');


INSERT INTO aircraft_type (name) VALUES
('Rotary Wing Aircraft'),
('Fixed Wind Aircraft'),
('RPAS');

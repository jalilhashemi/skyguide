--drop database skyguide;
--create database skyguide;
--use skyguide;

INSERT INTO activity_type (name) VALUES
('Airshow'),
('Calibration Flight'),
('Captive Balloon'),
('Contest'),
('Firework'),
('Flight With Hang Glider'),
('Gas Balloon'),
('Hot Air Balloon'),
('Kite'),
('Military Mission'),
('Parachute'),
('Photo Flight'),
('Police Mission'),
('Sky Lantern'),
('Sky Light / Laser'),
('Survey Flight'),
('TV Relay Flight'),
('Taxi Passenger Flight'),
('Test Flight'),
('Toy Balloon'),
('Transport Flight'),
('Weather Balloon'),
('Model Rocket');

INSERT INTO height_type (name) VALUES
('none'),
('ft AMSL'),
('ft GND'),
('m GND'),
('FL');


INSERT INTO aircraft_type (name) VALUES
('none'),
('Rotary Wing Aircraft'),
('Fixed Wing Aircraft'),
('RPAS'),
('Glider'),
('RPAS (e.g. ADS95)');

INSERT INTO drawing_type (name) VALUES
('Point'),
('Circle'),
('Path'),
('Polygon');

INSERT INTO field (name, label, placeholder, mandatory) VALUES
("field_callsign", "Callsign", "Callsign", TRUE),
("field_callsign", "Callsign", "Callsign", FALSE);

INSERT INTO form_type (activity_type_id, aircraft_type_id) VALUES
    ((SELECT id from activity_type WHERE name='Airshow'), (SELECT id from aircraft_type WHERE name='Rotary Wing Aircraft' )),
	((SELECT id from activity_type WHERE name='Calibration Flight'),( SELECT id from aircraft_type WHERE name='Rotary Wing Aircraft') );

INSERT INTO form_type_field(form_type_id, field_id) VALUES
((SELECT id from form_type WHERE id=1), (SELECT id from field WHERE id=1)),
((SELECT id from form_type WHERE id=2), (SELECT id from field WHERE id=2));
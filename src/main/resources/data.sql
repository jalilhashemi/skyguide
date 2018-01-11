--drop database skyguide;
--create database skyguide;

--use skyguide;

INSERT INTO activity_type (name) VALUES
('Airshow'),
('Calibration Flight'),
('Captive'),
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
('Sky Light / Lase'),
('Survey Flight'),
('TV Relay Flight'),
('Taxi Passenger Flight'),
('Test Flight'),
('Toy Balloon'),
('Transport Flight'),
('Weather Balloon');

INSERT INTO height_type (name) VALUES
('ft AMSL'),
('ft GND'),
('m GND'),
('FL');


INSERT INTO aircraft_type (name) VALUES
('Rotary Wing Aircraft'),
('Fixed Wing Aircraft'),
('RPAS'),
('Glider');

INSERT INTO drawing_type (name) VALUES
('Point'),
('Circle'),
('Path'),
('Polygon');

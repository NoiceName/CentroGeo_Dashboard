DROP SCHEMA if exists "projectSchema" cascade;

CREATE SCHEMA "projectSchema"; 

create table "projectSchema".Simulation(
	simulation_id serial constraint simulation_id_pk primary key,
	name text,
	date date,
	tags text,
	description text
);

create table "projectSchema".Snapshot(
	snapshot_id serial constraint snapshot_id_pk primary key,
	simulation_id serial constraint simulation_id_fk not null 
		references "projectSchema".Simulation (simulation_id) on delete cascade,
	time real
);

create table "projectSchema".Lane(
	lane_id text constraint lane_id_pk primary key,
	index integer,
	length real,
	shape text
);

create table "projectSchema".SnapshotLane(
	snapshot_lane_id serial constraint snapshot_lane_id_pk primary key,
	lane text constraint lane_fk not null
		references "projectSchema".Lane (lane_id) on delete cascade,
	snapshot serial constraint snapshot_fk not null
		references "projectSchema".Snapshot (snapshot_id) on delete cascade
);

create table "projectSchema".User(
	user_id serial constraint user_id_pk primary key,
	password_hash text
);


create table "projectSchema".Vehicle(
	vehicle_id text constraint vehicle_id_pk primary key,
	depart real
);

create table "projectSchema".SnapshotVehicle(
	snapshot_vehicle_id serial constraint snapshot_vehicle_id_pk primary key,
	vehicle text constraint vehicle_fk not null
		references "projectSchema".Vehicle (vehicle_id) on delete cascade,
	snapshot serial constraint snapshot_fk not null
		references "projectSchema".Snapshot (snapshot_id) on delete cascade,
	snapshot_lane serial constraint snapshot_lane_fk not null
		references "projectSchema".SnapshotLane (snapshot_lane_id) on delete cascade,
	type text,
	speedFactor real, 
	state text,
	pos float, 
	speed float,
	posLat float
);

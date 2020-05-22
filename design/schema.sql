drop schema if exists "projectschema" cascade;

create schema "projectschema"; 

create table "projectschema".simulation(
	simulation_id serial primary key,
	name text,
	date date,
	tags text,
	description text
);

create table "projectschema".snapshot(
	snapshot_id serial primary key,
	simulation integer not null 
		references "projectschema".simulation (simulation_id) on delete cascade,
	time real
);

create table "projectschema".lane(
	lane_id text primary key,
	index integer,
	length real,
	shape text
);

create table "projectschema".snapshotlane(
	snapshot_lane_id serial primary key,
	lane text not null
		references "projectschema".lane (lane_id) on delete cascade,
	snapshot integer not null
		references "projectschema".snapshot (snapshot_id) on delete cascade
);

create table "projectschema".user(
	user_id serial primary key,
	password_hash text
);


create table "projectschema".vehicle(
	vehicle_id text primary key,
	depart real
);

create table "projectschema".snapshotvehicle(
	snapshot_vehicle_id serial primary key,
	vehicle text constraint vehicle_fk not null
		references "projectschema".vehicle (vehicle_id) on delete cascade,
	snapshot integer constraint snapshot_fk not null
		references "projectschema".snapshot (snapshot_id) on delete cascade,
	snapshot_lane integer not null
		references "projectschema".snapshotlane (snapshot_lane_id) on delete cascade,
	type text,
	speedfactor real, 
	state text,
	pos float, 
	speed float,
	poslat float
);

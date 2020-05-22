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





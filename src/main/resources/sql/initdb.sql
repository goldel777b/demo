
drop table if exists abstract_person CASCADE;
drop table if exists dependent CASCADE ;
drop table if exists enrollee CASCADE  ;
drop table if exists enrollee_dependents CASCADE; 
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table abstract_person (
	id bigint not null, 
	birth_year integer not null, 
	birth_month integer not null, 
	birth_day_of_month integer not null, 
	db_version bigint not null, 
	first_name varchar(255) not null, 
	last_name varchar(255) not null, 
	middle_name varchar(255) not null, 
	primary key (id)
);


create table dependent (
	id bigint not null, 
	primary key (id)
);


create table enrollee (
    active boolean, 
    phone varchar(255), 
    id bigint not null, 
    primary key (id)
);

create table enrollee_dependents (
	enrollee_id bigint not null, 
	dependents_id bigint not null, 
	primary key (enrollee_id, dependents_id)
);


alter table enrollee_dependents add constraint UK_hudf2h1wxnuw1k1iox91kgbwy unique (dependents_id);

alter table dependent add constraint FK2rbj4tujv5sqmco849wjlj8c0 foreign key (id) references abstract_person;
 
alter table enrollee add constraint FK6kdlqy9i76unvr483c2dkf44s foreign key (id) references abstract_person;

alter table enrollee_dependents add constraint FKsmw1xpil3b8teue4f32nalbvn foreign key (dependents_id) references dependent;

alter table enrollee_dependents add constraint FK30n1nmr7tkuktbv3c1yygky1x foreign key (enrollee_id) references enrollee;

create database if not exists rest_api;

use rest_api;

create table if not exists user (
	user_id int auto_increment primary key,
    name varchar(50)
);

create table if not exists file (
	file_id int auto_increment primary key,
    name varchar(50),
    file_path varchar(150)
);

create table if not exists event (
	event_id int auto_increment primary key,
    user_id int,
    file_id int,
    foreign key (user_id) references user (user_id) on delete cascade,
    foreign key (file_id) references file (file_id) on delete cascade
);
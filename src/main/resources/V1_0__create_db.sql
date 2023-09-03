create database if not exists rest_api;

use rest_api;

create table if not exists users (
	user_id int auto_increment primary key,
    name varchar(50)
);

create table if not exists files (
	file_id int auto_increment primary key,
    name varchar(50),
    file_path varchar(150)
);

create table if not exists events (
	event_id int auto_increment primary key,
    user_id int,
    file_id int,
    foreign key (user_id) references users (user_id) on delete cascade,
    foreign key (file_id) references files (file_id) on delete cascade
);
create schema if not exists users;
create schema if not exists security;

create table if not exists users.personal_data(
    pk bigserial primary key,
    name varchar(255),
    cpf varchar(11),
    gender varchar(70),
    active boolean default true
);

create table if not exists users.users(
    pk bigserial primary key,
    email varchar(255),
    password varchar(255),
    active boolean default true,
    date_created timestamp,
    personal_data_id bigint
        constraint fk_personal_data
            references users.personal_data(pk)
);
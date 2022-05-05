
-- users module
create table users (
    id int(10) not null auto_increment primary key,
    email varchar(150) not null,
    password varchar(60) not null,
    user_type varchar(5) not null,
    name varchar(100) not null,
    phone_number varchar(20) not null,
    address varchar(200),
    unique key unique_email (email)
) ENGINE=INNODB;

-- customers module

create table customers (
    id int(10) not null auto_increment primary key,
    email varchar(150) not null,
    name varchar(100) not null,
    phone_number varchar(20) not null,
    address varchar(200),
    accepts_notifications tinyint(1) default 0,
    unique key unique_email (email),
    index (phone_number)
) ENGINE=INNODB;

create table pets (
    id int(10) not null auto_increment primary key,
    pet_owner int(10) not null,
    name varchar(100) not null,
    species varchar(50) not null,
    breed varchar(50) not null,
    sex varchar(1) not null,
    birth_date date,
    age int(3) not null,
    constraint fk_pet_pet_owner
        foreign key (pet_owner)
        references customers(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=INNODB;

create table shot_entries (
    id int(10) not null auto_increment primary key,
    pet int(10) not null,
    shot_date date not null,
    next_shot_date date not null,
    notes varchar(200),
    constraint fk_shot_entry_pet
        foreign key (pet)
        references pets(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=INNODB;

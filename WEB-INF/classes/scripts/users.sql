-- Absar Syed
-- 2/13/2024
-- SQL script for creating the Users table

DROP TABLE IF EXISTS Users;

DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;

CREATE TABLE Users (
    userid  	 int 		  NOT NULL PRIMARY KEY,
    password     varchar(66)  NOT NULL ,
    firstName 	 varchar(100) NOT NULL,
    lastName 	 varchar(100) NOT NULL,
    emailAddress varchar(100) NOT NULL UNIQUE ,
    lastAccess 	 varchar(15)  NOT NULL,
    enrolDate 	 varchar(15)  NOT NULL,
    enabled 	 boolean      NOT NULL,
    type 		 char         NOT NULL
);

INSERT INTO Users (userid, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type) VALUES

    (100763281, (encode(digest('password', 'sha256'), 'hex')), 'Adam', 'Kunz', 'adam.kunz@durhamcollege.ca', '2015-01-12', '2015-01-12', true, 'f'),
    (100123098, (encode(digest('password', 'sha256'), 'hex')), 'John', 'Doe', 'john.doe@durhamcollege.ca', '2020-09-11', '2020-09-11', true, 'f'),
    (100987123, (encode(digest('password', 'sha256'), 'hex')), 'Gonzales', 'Metro', 'gonzales.metro@durhamcollege.ca', '2019-09-11', '2019-09-11', true, 'f'),

    (100706764, (encode(digest('password', 'sha256'), 'hex')), 'Absar', 'Syed', 'absar.syed@dcmail.ca', '2021-09-08', '2021-09-08', true, 's'),
    (100111111, (encode(digest('password', 'sha256'), 'hex')), 'Mike', 'Jones', 'mike.jones@dcmail.ca',  '2024-02-02', '2015-09-11', true, 's'),
    (100275109, (encode(digest('password', 'sha256'), 'hex')), 'Luke', 'Skywalker', 'luke.skywalker@dcmail.ca',  '1977-05-25', '1977-05-25', true, 's');

-- SELECT * FROM users;


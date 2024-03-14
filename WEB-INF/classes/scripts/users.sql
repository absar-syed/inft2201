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
    enabled 	 varchar(10)  NOT NULL,
    type 		 varchar(2)   NOT NULL
);

INSERT INTO Users (userid, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type) VALUES

    (100763281, (encode(digest('12121212', 'sha256'), 'hex')), 'Adam', 'Kunz', 'adam.kunz@durhamcollege.ca', CURRENT_DATE, '2015-01-12', 'yes', 'f'),
    (100123098, (encode(digest('12345678', 'sha256'), 'hex')), 'John', 'Doe', 'john.doe@durhamcollege.ca', CURRENT_DATE, '2020-09-11', 'yes', 'f'),
    (100987123, (encode(digest('abcdefgh', 'sha256'), 'hex')), 'Gonzales', 'Metro', 'gonzales.metro@durhamcollege.ca', CURRENT_DATE, '2019-09-11', 'yes', 'f'),

    (100706764, (encode(digest('student', 'sha256'), 'hex')), 'Absar', 'Syed', 'absar.syed@dcmail.ca', CURRENT_DATE, '2021-09-08', 'yes', 's'),
    (100111111, (encode(digest('password', 'sha256'), 'hex')), 'Mike', 'Jones', 'mike.jones@dcmail.ca',  '2024-02-02', '2015-09-11', 'yes', 's'),
    (100275109, (encode(digest('security', 'sha256'), 'hex')), 'Luke', 'Skywalker', 'luke.skywalker@dcmail.ca',  CURRENT_DATE, '1977-05-25', 'yes', 's');

-- SELECT * FROM users;


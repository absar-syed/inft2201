-- Absar Syed
-- 2/13/2024
-- SQL script for creating the Students table

DROP TABLE IF EXISTS Students;

DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;

CREATE TABLE Students (
    userid              int          REFERENCES Users(userid) NOT NULL PRIMARY KEY,
    programcode 		varchar(10)  NOT NULL,
    programdescription  varchar(100) NOT NULL,
    year 				int 		 NOT NULL
);

INSERT INTO Students (userid, programcode, programdescription, year) VALUES
    (100706764, 'CPGA', 'Computer Programming & Analysis', 2),
    (100111111, 'CSTU', 'Computer Systems Technician', 1),
    (100275109, 'CPGA', 'Computer Programming & Analysis', 3);

-- SELECT * FROM students;

-- SELECT *
-- FROM Students
-- JOIN Users ON Students.userid = Users.userid;
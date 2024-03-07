-- Absar Syed
-- 2/13/2024
-- SQL script for creating the Faculty table

DROP TABLE IF EXISTS Faculty;

DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;


CREATE TABLE Faculty (
     userid             int          REFERENCES Users(userid) NOT NULL PRIMARY KEY,
     schoolcode 		varchar(100) NOT NULL,
     schooldescription 	varchar(100) NOT NULL,
     office 			varchar(100) NOT NULL,
     extension 			int 		 NOT NULL
);

INSERT INTO Faculty (userid, schoolcode, schooldescription, office, extension) VALUES
    (100763281, 'CPGA', 'Computer Programming & Analysis', 'C132', 9012),
    (100123098, 'CSTU', 'Computer Systems Technician', 'H113', 2376),
    (100987123, 'CSTY', 'Computer System Technology', 'SW210', 1298);

-- SELECT * FROM Faculty;

-- SELECT *
-- FROM Faculty
-- JOIN Users ON Faculty.userid = Users.userid;

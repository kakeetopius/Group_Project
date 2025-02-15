CREATE TABLE Course (
    courseid SERIAL NOT NULL PRIMARY KEY,
    coursename VARCHAR(50) UNIQUE NOT NULL,
    credit INT NOT NULL,
    lecid INT
);

CREATE TABLE Lecturer (
    lecid SERIAL NOT NULL PRIMARY KEY,
    fname VARCHAR(50),
    lname VARCHAR(50), 
    email VARCHAR(50) UNIQUE,
    phone VARCHAR(50) UNIQUE,
    dob VARCHAR(50),
    dept VARCHAR(50),
    gender CHAR(1) CHECK(gender IN ('M', 'F'))
);

CREATE TABLE Student (
    stdid SERIAL NOT NULL PRIMARY KEY,
    fname VARCHAR(50),
    lname VARCHAR(50), 
    email VARCHAR(50) UNIQUE,
    phone VARCHAR(50) UNIQUE,
    dob VARCHAR(50),
    faculty VARCHAR(50),
    gender CHAR(1) CHECK(gender IN ('M', 'F'))
);

CREATE TABLE Enrollment (
    enrollmentid SERIAL NOT NULL PRIMARY KEY,
    courseid INT NOT NULL,
    stdid INT NOT NULL, 
    grade INT
);

ALTER TABLE Course 
ADD CONSTRAINT fkey_lec_id FOREIGN KEY(lecid) 
REFERENCES Lecturer(lecid);

ALTER TABLE Enrollment
ADD CONSTRAINT fkey_course_id FOREIGN KEY(courseid)
REFERENCES Course(courseid);

ALTER TABLE Enrollment
ADD CONSTRAINT fkey_std_id FOREIGN KEY(stdid)
REFERENCES Student(stdid);
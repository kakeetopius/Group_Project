INSERT INTO Course (coursename, credit)
VALUES 
    ('Discrete Maths', 3),
    ('Calculus', 3),
    ('Computer Fundamentals', 3),
    ('Maisha', 0);

INSERT INTO Lecturer (fname, lname, email, phone, dob, dept, gender) 
VALUES 
    ('John', 'Kent', 'jk@gmail.com', '0703249531', '1999-03-22', 'SCES', 'M'),
    ('Jane', 'Hobbs', 'jhobbs@gmail.com', '0789391901', '1992-08-23', 'SLS', 'F'),
    ('Paul', 'Opio', 'popio@gmail.com', '0930193014', '2003-09-22', 'SCES', 'M'),
    ('Mary', 'Apio', 'apio@gmail.com', '0784920435', '1990-02-19', 'SIMS', 'F');

INSERT INTO Student (fname, lname, email, phone, dob, faculty, gender)
VALUES
    ('Jacob', 'Harrison', 'jharrison@gmail.com', '0709340233', '2005-09-21', 'SCES', 'M'),  
    ('Esther', 'Mitchell', 'emitchell@gmail.com', '0704990301', '2003-03-22', 'SIMS', 'F'),
    ('Olivia', 'Hudson', 'ohudson@gmail.com', '0703290190', '2004-03-02', 'SCES', 'F'),
    ('Emmanuel', 'Juma', 'ejuma@gmail.com', '0700920911', '2001-09-04', 'SLS', 'F');

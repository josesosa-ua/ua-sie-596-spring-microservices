-- Insert data into the Person table
INSERT INTO person (id, name, major, dept, date_of_birth, phone, email) VALUES
(1, 'John Doe', 'Computer Science', 'Engineering', '1995-01-01', '555-1234', 'john.doe@example.com'),
(2, 'Jane Smith', 'Mathematics', 'Science', '1996-02-02', '555-5678', 'jane.smith@example.com'),
(3, 'Jose Sosa', 'Software Engineering', 'Engineering', '1984-08-11', '555-7778', 'josesosa@arizona.edu'),
(4, 'Valentina Paralitici', 'Biostatistics', 'Public Health', '1985-12-12', '555-1894', 'vparalitici@example.com');

-- Insert data into the Organization table
INSERT INTO organization (id, name, category, established_date, president_id) VALUES
(1, 'Tennis Club', 'Sports', '2010-09-01', 3),
(2, 'Poetry Club', 'Arts', '2012-01-15', 4);

-- Insert data into the person_organization join table
INSERT INTO person_organization (organization_id, person_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 3),
(2, 4);

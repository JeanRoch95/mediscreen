USE mediscreen_patient;

CREATE TABLE IF NOT EXISTS patient (
                                       patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       firstname VARCHAR(255),
                                       lastname VARCHAR(255),
                                       dateofbirth DATE,
                                       address VARCHAR(255),
                                       phonenumber VARCHAR(255),
                                       gender VARCHAR(50)
);g


INSERT INTO patient (firstname, lastname, dateofbirth, gender, address, phonenumber) VALUES
                                                                                         ('TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
                                                                                         ('TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444'),
                                                                                         ('TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
                                                                                         ('TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');


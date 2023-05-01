-- Initialize tables and data for user-service
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER
);

INSERT INTO users (name, age) VALUES
    ('name1', 40),
    ('name2', 45),
    ('name3', 50);


-- Initialize tables and data for payment-processing-service
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO payments (name) VALUES ('payment1');
INSERT INTO payments (name) VALUES ('payment2');
INSERT INTO payments (name) VALUES ('payment3');


-- Initialize tables and data for risk-management-service
CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO requests (name) VALUES ('request1');
INSERT INTO requests (name) VALUES ('request2');
INSERT INTO requests (name) VALUES ('request3');
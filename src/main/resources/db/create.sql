SET MODE PostgresSQL;

CREATE DATABASE utalii;
\c utalii;

CREATE TABLE IF NOT EXISTS tours (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    imageUrl VARCHAR,
    imageId INT,
    price int
);



CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    tourId INT,
    imageData bytea
);


CREATE TABLE IF NOT EXISTS reviews (
 id SERIAL PRIMARY KEY,
 author VARCHAR,
 rating VARCHAR,
 content VARCHAR,
 kidId INTEGER,
 createdat BIGINT
);

CREATE DATABASE utalii_test WITH TEMPLATE utalii;
SET MODE PostgresSQL;

CREATE DATABASE utalii;
\c utalii;

CREATE TABLE IF NOT EXISTS tours (
id SERIAL PRIMARY KEY,
tourName VARCHAR,
tourDescription VARCHAR,
imageUrl VARCHAR,
price int
);

CREATE TABLE IF NOT EXISTS reviews (
 id SERIAL PRIMARY KEY,
 author VARCHAR,
 rating VARCHAR,
 content VARCHAR,
 tourId INTEGER,
 createdat BIGINT
);

CREATE DATABASE utalii_test WITH TEMPLATE utalii;
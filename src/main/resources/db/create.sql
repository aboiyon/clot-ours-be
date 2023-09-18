SET MODE PostgresSQL;

CREATE DATABASE utalii;
\c utalii;

CREATE TABLE IF NOT EXISTS tours (
id int SERIAL PRIMARY KEY,
name VARCHAR,
description VARCHAR,
imageUrl VARCHAR,
price int
);

CREATE TABLE reviews (
 id int SERIAL PRIMARY KEY,
 writtenby VARCHAR,
 rating VARCHAR,
 content VARCHAR,
 restaurantid INTEGER,
 createdat BIGINT
);

CREATE DATABASE utalii_test WITH TEMPLATE utalii;
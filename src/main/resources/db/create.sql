SET MODE PostgresQL;

CREATE DATABASE utalii;
\c utalii;

CREATE TABLE IF NOT EXISTS tours (
id SERIAL PRIMARY KEY,
tourName VARCHAR,
tourDescription VARCHAR,
imageUrl VARCHAR,
price int
);

CREATE TABLE reviews (
 id SERIAL PRIMARY KEY,
 writtenby VARCHAR,
 rating VARCHAR,
 content VARCHAR,z
 tourid INTEGER,
 createdat BIGINT
);

CREATE DATABASE utalii_test WITH TEMPLATE utalii;
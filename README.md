CREATE DATABASE best_restaurant;
CREATE DATABASE best_restaurant_test WITH TEMPLATE best_restaurant;

\c best_restaurant;

CREATE TABLE restaurants (id serial PRIMARY KEY, restName varchar);

ALTER TABLE restaurants ADD cuisineId int;

CREATE TABLE cuisines (id serial primary key, name varchar);

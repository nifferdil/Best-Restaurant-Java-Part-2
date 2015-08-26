Guest=# CREATE DATABASE best_restaurant;
CREATE DATABASE best_restaurant_test WITH TEMPLATE best_restaurant;

Guest-# \c best_restaurant;

best_restaurant=# CREATE TABLE restaurants (id serial PRIMARY KEY, restName varchar);
CREATE TABLE
best_restaurant=# ALTER TABLE restaurants ADD cuisineId int;
ALTER TABLE
best_restaurant=# create table cuisines (id serial primary key, name varchar);
CREATE TABLE

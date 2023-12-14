-- liquibase formatted sql
-- changeset taron-dev:V20231119184645__initTables.sql

-- Create Category table
CREATE TABLE IF NOT EXISTS category (
  id BIGINT PRIMARY KEY,
  name VARCHAR(255),
  label VARCHAR(255),
  category_id BINARY(16) NOT NULL UNIQUE
);

ALTER TABLE category MODIFY id BIGINT AUTO_INCREMENT;
ALTER TABLE category AUTO_INCREMENT=100;

-- Create Intention table
CREATE TABLE IF NOT EXISTS intention (
  id BIGINT PRIMARY KEY,
  text VARCHAR(255),
  category_id BIGINT,
  intention_id BINARY(16) NOT NULL UNIQUE
  --FOREIGN KEY (category_id) REFERENCES category(id) - not supported by planetscale free db
);

ALTER TABLE intention MODIFY id BIGINT AUTO_INCREMENT;
ALTER TABLE intention AUTO_INCREMENT=100;
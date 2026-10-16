--liquibase formatted sql

--changeset tu-nombre:004-change
ALTER TABLE appointments ADD COLUMN notes VARCHAR(500);
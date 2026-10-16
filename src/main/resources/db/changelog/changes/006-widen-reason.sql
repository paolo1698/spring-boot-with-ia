--liquibase formatted sql

--changeset tu-nombre:006-change
ALTER TABLE appointments ALTER COLUMN reason VARCHAR(255);
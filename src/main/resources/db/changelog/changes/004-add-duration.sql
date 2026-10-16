--liquibase formatted sql

--changeset persona-b:004-change
ALTER TABLE appointments ADD COLUMN duration_minutes INT DEFAULT 30;
--rollback ALTER TABLE appointments DROP COLUMN duration_minutes;

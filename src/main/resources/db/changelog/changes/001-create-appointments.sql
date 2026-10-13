--liquibase formatted sql

--changeset persona-a:001-create-appointments
CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    date_time TIMESTAMP NOT NULL,
    reason VARCHAR(255)
);
--rollback DROP TABLE appointments;
--liquibase formatted sql

--changeset persona-a:001-create-appointments
CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    date_time TIMESTAMP NOT NULL,
    reason VARCHAR(255)
);
--Reason es opcional puede estar vacio, por eso no tiene NOT NULL
--rollback DROP TABLE appointments;
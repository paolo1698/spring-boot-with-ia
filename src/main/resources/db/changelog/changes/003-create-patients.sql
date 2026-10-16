--liquibase formatted sql

--changeset persona-b:003-create-patients
CREATE TABLE patients (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150)
);
--rollback DROP TABLE patients;

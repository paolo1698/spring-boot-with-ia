package com.example.springBootIA.persistence.repositories;

import com.example.springBootIA.persistence.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    // Spring Data JPA deduce la consulta SQL automáticamente por el nombre del método
    boolean existsByDateTime(LocalDateTime dateTime);
}
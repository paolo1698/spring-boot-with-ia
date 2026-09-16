package com.example.springBootIA.handlers.commands;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ScheduleAppointmentHandler {

    // El comando que transporta los datos de entrada
    record Command(UUID patientId, LocalDateTime dateTime, String reason) {}
    Result handle(Command command);
    // Interfaz sellada con los posibles resultados del negocio
    sealed interface Result permits Result.Scheduled, Result.SlotNotAvailable {

        record Scheduled(
                UUID id,
                UUID patientId,
                LocalDateTime dateTime,
                String reason
        ) implements Result {}

        record SlotNotAvailable() implements Result {}
    }
}
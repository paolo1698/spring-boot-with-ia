package com.example.springBootIA.handlers.commands;

import java.time.LocalDateTime;
import java.util.UUID;


public interface ScheduleAppointmentHandler {
    //commands y results
    record Command(UUID patientId, LocalDateTime dateTime, String reason){}

    sealed interface Result permits Result.Scheduled, Result.SlotUnavailable{
        record Scheduled(UUID id, UUID patientId, LocalDateTime scheduledDateTime, String reason) implements Result {}
        record SlotUnavailable(LocalDateTime scheduledDateTime) implements Result {}
    }
}

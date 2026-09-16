package com.example.springBootIA.handlers.queries;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GetAppointmentByIdHandler {

    record Query(UUID id) {}

    Result handle(Query query);

    sealed interface Result permits Result.Found, Result.NotFound {
        record Found(
                UUID id,
                UUID patientId,
                LocalDateTime dateTime,
                String reason
        ) implements Result {}

        record NotFound() implements Result {}
    }
}
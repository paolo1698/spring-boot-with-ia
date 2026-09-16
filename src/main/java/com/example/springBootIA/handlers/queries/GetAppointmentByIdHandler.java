package com.example.springBootIA.handlers.queries;


import java.time.LocalDateTime;
import java.util.UUID;

public interface GetAppointmentByIdHandler {

    Result handle(Query query);

    record Query(UUID id) {
    }

    sealed interface Result permits Result.Found, Result.NotFound {
        record Found(UUID id, UUID patientId, LocalDateTime dateTime, String reason) implements Result {
        }

        record NotFound(UUID id) implements Result {
        }
    }
}
}
